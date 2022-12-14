package com.example.red.book.controller;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.example.red.book.common.api.CommonResult;
import com.example.red.book.common.api.ResultCode;
import com.example.red.book.common.exception.GlobalException;
import com.example.red.book.config.StsProperties;
import com.example.red.book.model.vo.FileVO;
import com.example.red.book.util.SessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Api(tags = "文件模块")
@RestController
@RequestMapping("/api/file")
public class FileController {
    @Autowired
    private StsProperties stsProperties;

    @Autowired
    private SessionUtil sessionUtil;

    @ApiOperation(value = "上传文件")
    @PostMapping("upload")
    public CommonResult<FileVO> upload(@RequestParam("files") MultipartFile[] files) throws IOException {
        String endpoint = stsProperties.getEndpoint();
        String accessKeyId = stsProperties.getKi();
        String accessKeySecret = stsProperties.getKs();
        String bucketName = stsProperties.getBucketName();
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        if (files == null || files.length == 0) {
            throw GlobalException.from(ResultCode.FILE_EMPTY);
        }
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = sdf.format(new Date());
            log.info("文件:{}", file.getInputStream());
            log.info("文件名 {}", file.getOriginalFilename());
            log.info("文件大小 {}", file.getSize());

            String objectName = sessionUtil.getUserId() + "/" + dateString + "/" + file.getOriginalFilename();

            try {
                // 创建InitiateMultipartUploadRequest对象。
                InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, objectName);

                // 如果需要在初始化分片时设置请求头，请参考以下示例代码。
                ObjectMetadata metadata = new ObjectMetadata();
                // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
                // 指定该Object的网页缓存行为。
                // metadata.setCacheControl("no-cache");
                // 指定该Object被下载时的名称。
                metadata.setContentDisposition("attachment;filename=" + file.getOriginalFilename());
                // 指定该Object的内容编码格式。
                // metadata.setContentEncoding(OSSConstants.DEFAULT_CHARSET_NAME);
                // 指定初始化分片上传时是否覆盖同名Object。此处设置为true，表示禁止覆盖同名Object。
                metadata.setHeader("x-oss-forbid-overwrite", "true");
                // 指定上传该Object的每个part时使用的服务器端加密方式。
                // metadata.setHeader(OSSHeaders.OSS_SERVER_SIDE_ENCRYPTION, ObjectMetadata.KMS_SERVER_SIDE_ENCRYPTION);
                // 指定Object的加密算法。如果未指定此选项，表明Object使用AES256加密算法。
                // metadata.setHeader(OSSHeaders.OSS_SERVER_SIDE_DATA_ENCRYPTION, ObjectMetadata.KMS_SERVER_SIDE_ENCRYPTION);
                // 指定KMS托管的用户主密钥。
                // metadata.setHeader(OSSHeaders.OSS_SERVER_SIDE_ENCRYPTION_KEY_ID, "9468da86-3509-4f8d-a61e-6eab1eac****");
                // 指定Object的存储类型。
                // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard);
                // 指定Object的对象标签，可同时设置多个标签。
                // metadata.setHeader(OSSHeaders.OSS_TAGGING, "a:1");
                // request.setObjectMetadata(metadata);

                // 初始化分片。
                InitiateMultipartUploadResult upresult = ossClient.initiateMultipartUpload(request);
                // 返回uploadId，它是分片上传事件的唯一标识。您可以根据该uploadId发起相关的操作，例如取消分片上传、查询分片上传等。
                String uploadId = upresult.getUploadId();
                // partETags是PartETag的集合。PartETag由分片的ETag和分片号组成。
                List<PartETag> partETags = new ArrayList<PartETag>();
                // 每个分片的大小，用于计算文件有多少个分片。单位为字节。
                final long partSize = 1 * 1024 * 1024L;   //1 MB。
                long fileLength = file.getSize();
                int partCount = (int) (fileLength / partSize);
                if (fileLength % partSize != 0) {
                    partCount++;
                }
                // 遍历分片上传。
                for (int i = 0; i < partCount; i++) {
                    long startPos = i * partSize;
                    long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
                    InputStream inputStream = file.getInputStream();
                    // 跳过已经上传的分片。
                    inputStream.skip(startPos);
                    UploadPartRequest uploadPartRequest = new UploadPartRequest();
                    uploadPartRequest.setBucketName(bucketName);
                    uploadPartRequest.setKey(objectName);
                    uploadPartRequest.setUploadId(uploadId);
                    uploadPartRequest.setInputStream(inputStream);
                    // 设置分片大小。除了最后一个分片没有大小限制，其他的分片最小为100 KB。
                    uploadPartRequest.setPartSize(curPartSize);
                    // 设置分片号。每一个上传的分片都有一个分片号，取值范围是1~10000，如果超出此范围，OSS将返回InvalidArgument错误码。
                    uploadPartRequest.setPartNumber(i + 1);
                    // 每个分片不需要按顺序上传，甚至可以在不同客户端上传，OSS会按照分片号排序组成完整的文件。
                    UploadPartResult uploadPartResult = ossClient.uploadPart(uploadPartRequest);
                    // 每次上传分片之后，OSS的返回结果包含PartETag。PartETag将被保存在partETags中。
                    partETags.add(uploadPartResult.getPartETag());
                }

                // 创建CompleteMultipartUploadRequest对象。
                // 在执行完成分片上传操作时，需要提供所有有效的partETags。OSS收到提交的partETags后，会逐一验证每个分片的有效性。当所有的数据分片验证通过后，OSS将把这些分片组合成一个完整的文件。
                CompleteMultipartUploadRequest completeMultipartUploadRequest =
                        new CompleteMultipartUploadRequest(bucketName, objectName, uploadId, partETags);
                // 如果需要在完成分片上传的同时设置文件访问权限，请参考以下示例代码。
                // completeMultipartUploadRequest.setObjectACL(CannedAccessControlList.Private);
                // 指定是否列举当前UploadId已上传的所有Part。如果通过服务端List分片数据来合并完整文件时，以上CompleteMultipartUploadRequest中的partETags可为null。
                // Map<String, String> headers = new HashMap<String, String>();
                // 如果指定了x-oss-complete-all:yes，则OSS会列举当前UploadId已上传的所有Part，然后按照PartNumber的序号排序并执行CompleteMultipartUpload操作。
                // 如果指定了x-oss-complete-all:yes，则不允许继续指定body，否则报错。
                // headers.put("x-oss-complete-all","yes");
                // completeMultipartUploadRequest.setHeaders(headers);
                // 完成分片上传。
                CompleteMultipartUploadResult completeMultipartUploadResult = ossClient.completeMultipartUpload(completeMultipartUploadRequest);
                log.info(completeMultipartUploadResult.getETag());
                urls.add("https://" + bucketName + "." + endpoint + "/" + objectName);
            } catch (OSSException oe) {
                log.error("Error Message:" + oe.getErrorMessage());
                log.error("Error Code:" + oe.getErrorCode());
                log.error("Request ID:" + oe.getRequestId());
                log.error("Host ID:" + oe.getHostId());
            } catch (ClientException ce) {
                log.error("Error Message:" + ce.getMessage());
            } catch (IOException e) {
                throw GlobalException.from(ResultCode.UPLOAD_ERROR);
            }
        }
        return CommonResult.success(new FileVO(urls));
    }
}
