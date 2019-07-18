# 文档翻译API简介

文档翻译API采用先进神经网络机器翻译引擎，获取高质量机器翻译结果，支持中文、英语、日语、韩语、俄语、德语、西班牙语、葡萄牙语等46种语言。其简单易懂的操作便于开发者接入，避免了繁杂的文档翻译研发工作，能够以低成本、高效率的优点在您的翻译工具中支持近40种文件格式和近50种语言的文件翻译。

点击查看代码示例：<https://github.com/tmxmall-code/api-demo/tree/master/docTrans-api>

# 文档翻译上传接口

__接口完整路径__：<https://www.tmxmall.com/qt/v1/http/qtFile/_upload>
__请求方式__：POST
__接口说明__：用户调用接口上传快翻源文件，上传完成后将自动进行翻译，并返回数据结果\[详见返回结果列表\]

> 注意：
> 1. 支持的上传格式：doc/docx，xls/xlsx，ppt/pptx，rtf，vsdx，ods，ots，odg，otp，odt，ott，html，htm，dita，ditamap，xliff，sdlxlf，xlf，xml，md，idml，mif，srt，jpg，png，pdf，txt，csv，yml，json。  
> 2. 文件限制：文件大小不超过100M，文件字符数不超过100000字,word文件或excel文件行数不能超过30000行。
> 3. file参数请以 multipart/form-data 方式的表单 POST 提交。
> 4. 所有的请求参数（除了file参数）都需经过字符集为UTF8的URL编码。

## 请求参数列表： 

| 参数名称 |参数类型 | 参数说明 | 是否必填 | 备注 |
| :--- | :--- | :--- | :--- | :--- |
| file_api_trans| MultipartFile | 上传的文件 | 是 | 用户自定义 |
| user_name | String | 用户名 | 是 | 在个人中心获取 |
| client_id | String | 用户识别码 | 是 | 在个人中心获取 |
| from | String | 源语言 | 是 | 详见语言列表 |
| to | String | 目标语言 | 是 | 详见语言列表 |

## 返回结果列表：

|参数名称|	参数类型|	参数说明|	是否必存在|	备注|
| :--- | :--- | :--- | :--- | :--- |
|errCode|	int|	返回的结果状态|	是|	详见错误表|
|errMsg|	String|	返回的结果信息|	是|	详见错误表|
|data	|String	|返回数据结果集	|否	|Json字符串|

## data结果集

|参数名称|	参数类型|	参数说明|	备注|
| :--- | :--- | :--- | :--- |
|docId	|String	|上传文件的唯一标识	|可通过该id查询翻译进度等|


# 文档翻译下载接口

__接口完整路径__：<https://www.tmxmall.com/qt/v1/http/qtFile/_download>
__请求方式__：GET
__接口说明__：用户调用接口下载翻译后的文件


## 请求参数列表：

| 参数名称 |参数类型 | 参数说明 | 是否必填 | 备注 |
| :--- | :--- | :--- | :--- | :--- |
| user_name | String | 用户名 | 是 | 在个人中心获取 |
| client_id | String | 用户识别码 | 是 | 在个人中心获取 |
| doc_id | String | 上传文件的唯一标识符 | 是 | 由文件上传接口返回得到|
| is_ie | String | 是否为IE浏览器 | 是 | y/n|



## 返回结果列表：

|参数名称|	参数类型|	参数说明|	是否必存在|	备注|
| :--- | :--- | :--- | :--- | :--- |
|errCode|	int|	返回的状态|	是|	详见错误表|
|errMsg|	String|	返回的结果信息|	是|	详见错误表|


# 文档翻译获取翻译进度接口

__接口完整路径__：<https://www.tmxmall.com/qt/v1/http/transProgress>
__请求方式__：GET
__接口说明__：用户调用接口实时获取上传文件的翻译进度


## 请求参数列表：

| 参数名称 |参数类型 | 参数说明 | 是否必填 | 备注 |
| :--- | :--- | :--- | :--- | :--- |
| user_name | String | 用户名 | 是 | 在个人中心获取 |
| client_id | String | 用户识别码 | 是 | 在个人中心获取 |
| doc_id | String | 上传文件的唯一标识符 | 是 | 由文件上传接口返回得到|

## 返回结果列表：

|参数名称|	参数类型|	参数说明|	是否必存在|	备注|
| :--- | :--- | :--- | :--- | :--- |
|errCode|	int|	返回的状态|	是|	详见错误表|
|errMsg|	String|	返回的结果信息|	是|	详见错误表|
|data	|String	|返回数据结果集	|否	|Json字符串|

## data结果集

|参数名称|	参数类型|	参数说明|	备注|
| :--- | :--- | :--- | :--- |
|docId	|String	|上传文件的唯一标识	|可通过该id查询翻译进度等|
|docName|文件名	|String	|上传文件名|
|docType	|文件类型	|String	||
|srcLan	|源语言	|String	|详见语言列表|
|tgtLan	|目标语言	|String	|详见语言列表|
|percent	|翻译进度	|String	||
|pageNum	|文件总页数	|int	||
|words	|文件总字数	|int	||
|chars|文件总字符数	|int	||





## 语言列表 

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


## 返回状态列表

|错误码	|错误说明|
| :--- | :--- |
|0|	成功|
|TB01001|	参数不合法|
|TB1076|	上传文件无效|
|TB01021|	用户不存在|
|TB01002	|系统错误(解析失败，导出失败)|
|TB01070	|权限错误|
|TB01073 |余额不足|
















