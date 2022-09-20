package com.example.red.book.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileVO {
    @ApiModelProperty("文件链接,多个文件链接用逗号分隔")
    private List<String> fileUrl;

}
