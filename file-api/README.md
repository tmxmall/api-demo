# 文件解析API简介

文档解析API提供了极其简单的编程接口，可以快速解析指定格式的文件，返回从文件中提取的文本和样式数据对象。

开发者翻译提取的文本后返回数据对象，平台给您生成与原文格式排版保持一致的译文文件。

借助文档解析API，开发者可以避免繁琐的文件解析研发工作，能够以极低成本，在您的翻译工具中支持超过30种文件格式、近50种语言的文件翻译。

**接入流程**

开发者可以填写申请接入表单获取调试的必要参数信息：[http://n7uxjrpr7qh5s56f.mikecrm.com/NIh5oJ5?tdsourcetag=s\_pcqq\_aiomsg](http://n7uxjrpr7qh5s56f.mikecrm.com/NIh5oJ5?tdsourcetag=s_pcqq_aiomsg)

API**价格**

每次导出收取费用2元，重复导出不收取费用，量大可以申请优惠。
# 文件上传解析接口

**接口完整路径**：`http://api.tmxmall.com/v1/http/parseFile`
**请求方式**：`POST`
**接口说明**：用户调用接口上传解析源文件，并返回解析后的数据结果\[详见返回结果列表\]

> 注意：
> 1. 支持的上传格式：pdf 、doc、ppt、xls、docx、pptx、xlsx、txt、xliff、sdlxlf、xlf、xml、csv、idml、html、htm、md、vsdx、mif、ods、yml、ots、odg、otp、odt、ott、srt、rtf、json、dita、ditamap。  
> 2. 文件限制：文件大小不超过100M，文件字符数不超过100000字,word文件或excel文件行数不能超过30000行。
> 3. file参数请以 multipart/form-data 方式的表单 POST 提交。
> 4. 所有的请求参数（除了file参数）都需经过字符集为UTF8的URL编码。

## 请求参数列表：

| 参数名称 |参数类型 | 参数说明 | 是否必填 | 备注 |
| :--- | :--- | :--- | :--- | :--- |
| file | MultipartFile | 上传的文件 | 是 | 用户自定义 |
| user_name | String | 用户名 | 是 | 在个人中心获取 |
| client_id | String | 用户识别码 | 是 | 在个人中心获取 |
| from | String | 源语言 | 是 | 详见语言列表 |
| to | String | 目标语言 | 是 | 详见语言列表 |
| de | String | 调用方 | 否 | ||

## 返回结果列表：

|参数名称|	参数类型|	参数说明|	是否必存在|	备注|
| :--- | :--- | :--- | :--- | :--- |
|docId	|String	|文件的唯一标识	|否	||
|segments	|List<SimpleSegment>	|文件解析的结果	|否|	详见SimpleSegment表|
|errCode|	int|	解析的结果状态|	是|	详见错误表|
|errMsg|	String|	解析的结果信息|	是|	详见错误表|


# 文件导出接口

**接口名称**：`exportFile`

**接口完整路径**：`http://api.tmxmall.com/v1/http/exportFile`

**请求方式**：`POST`

**接口说明**：用户调用接口生成翻译文件，并返回生成文件的fileId。

> 注意: 所有的请求参数都需经过字符集为UTF8的URL编码。


## 请求参数列表：

|参数名称	|参数类型	|参数说明	|是否必填	|备注|
| :--- | :--- | :--- | :--- | :--- |
|user_name|	String|	用户名称|	是|	在个人中心获取|
|client_id|	String|	用户识别码|	是	|在个人中心获取|
|doc_id	|String|	文件的唯一标识	|是	|文件解析之后返回结果|
|segments	|List<SimpleSegment>|	经过修改的segments|	是	| 由上传解析接口返回得到|


返回参数：导出的文件流

**SimpleSegment：**

|字段 |	数据类型	|解释|
| :--- | :--- | :--- |
|_id	|String	|segmentId|
|documentId	|String	|翻译文件Id|
|translationUnitId|	String|	翻译句段Id|
|srcSegmentAtoms|	List<Atom>|	分句后的原文内容|
|tgtSegmentAtoms	|List<Atom>|	分句后的翻译内容|

**Atom：**

|字段	|数据类型|解释|
| :--- | :--- | :--- |
|atomId	|String|	从0开始|
|data	|String	|数据内容|
|textStyle	|String	|类别regular,tag|
|isHidden|boolean|是否隐藏|
|tagType|String	|Tag的类型 OPENING , CLOSING , PLACEHOLDER , VIRTUAL_OPENING ,VIRTUAL_CLOSING|
|tagId	|int|Tag编号|
|isCustom	|boolean|是否是自定义|

**语言列表**

| 语言简写 | 名称 |
| :---: | :---: |
| zh-CN | 中文简体 |
| zh-TW | 中文台湾 |
| zh-HK | 中文香港 |
| en-US | 英语美国 |
| en-GB | 英语英国 |
| ja-JP | 日语 |
| ko-KR | 韩语 |
| ru-RU | 俄语 |
| de-DE | 德语 |
| es-ES | 西班牙语 |
| fr-FR | 法语 |
| pt-PT | 葡萄牙语 |
| ar-SD | 阿拉伯语 |
| th-TH | 泰语 |
| vi-VN | 越南语 |
| my-MM | 缅甸语 |
| id-ID | 印尼语 |
| km-KH | 高棉语 |
| lo-LA | 老挝语 |
| ms-MY | 马来西亚语 |
| fil-P | 菲律宾语 |
| el-GR | 希腊语 |
| it-IT | 意大利语 |
| tr-TR | 土耳其语 |
| uk-UA | 乌克兰语 |
| sv-SE | 瑞典语 |
| cs-CZ | 捷克语 |
| sk-SK | 斯洛伐克语 |
| sl-SL | 斯洛文尼亚语 |
| pl-PL | 波兰语 |
| da-DK | 丹麦语 |
| nl-NL | 荷兰语 |
| fi-FI | 芬兰语 |
| hu-HU | 匈牙利语 |
| hi-IN | 印地语 |
| he-IL | 希伯来语 |
| bn-BD | 孟加拉语 |
| hy-AM | 亚美尼亚语 |
| bo-CN | 藏语 |
| ug-CN | 维语 |
| ii-CN | 彝语 |
| la-00 | 拉丁语 |
| ro-RO | 罗马尼亚语 |
| bg-BG | 保加利亚语 |
| hr-HR | 克罗地亚语 |
| sq-AL | 阿尔巴里亚语 |
| mk-MK | 马其顿语 |
| et-EE | 爱沙尼亚语 |
| lt-LT | 立陶宛语 |



**返回状态列表:**

|错误码	|错误说明|
| :--- | :--- |
|0|	成功|
|TB01001|	参数不合法|
|TB1076|	上传文件无效|
|TB01021|	用户不存在|
|TB01002	|系统错误(解析失败，导出失败)|
|TB01070	|权限错误|

