# 翻译记忆库API简介

## 以Tmxmall语料快搜亿万句对语料为依托
为CAT软件、在线CAT平台、翻译众包网站、移动端词典APP提供API接口，输出高质量参考例句，提升翻译质量。
## 通过Tmxmall云翻译记忆库开放API
可在已经集成开放API的CAT软件中查询Tmxmall语料快搜亿万句对语料，并且同时可查询语料云管家账户的私有库，
随时随地查询云端语料数据。
## 通过语料云管家实时写入API
用户在翻译的过程中，可以将翻译好的句对实时地写入到云端的语料云管家记忆库中，实现团队协作成员实时共享，
翻译记忆库，避免多人重复翻译。

# 用户ClientId验证接口

翻译记忆库 api 集成商在用户设置账号的时候需要验证账号信息是否正确。 验证接口如下：

## 接口 URL

```
http://api.tmxmall.com/v1/http/clientIdVerify?user_name=XXXX&client_id=12334455&de=trados
```

> get 请求，无需用户登录

输入参数：

| 参数名称 | 说明 | 参数类型 | 是否为空 |
| :---: | :---: | :---: | :---: |
| user\_name | 用户 Tmxmall邮箱账号 | 字符 | N |
| client\_id | 用户clientId | 字符 | N |
| de | 调用方 | 字符 | N |

> 说明：client\_id 即为 tmxmall 的 API Key，client\_id 需要登录到 Tmxmall 网站个人中心查看。

返回参数：

| 参数名称 | 说明 | 参数类型 | 是否为空 |
| :---: | :---: | :---: | :---: |
| error\_code | 错误码 | 字符 | N |
| error\_msg | 错误信息 | 字符 | N |

`返回数据为 json 格式，示例如下：
成功：{"error_code":"0","error_msg":"成功"}
失败：{"error_code":"1","error_msg":"系统异常"}`

目前可能出现的错误码有 TB01002，TB01001，TB01021 三种，其他未知错误统称为系
统错误。

| 错误码 | 错误说明 |
| :--- | :--- |
| TB01002 | 系统错误 |
| TB01001 | 参数不合法\(包括clientId不匹配\) |
| TB01021 | 用户名不存在 |

其他异常情况:
验证接口超时时间为 5 秒，5 秒无信息返回，软件可认为是 Tmxmall 网站超时或者
是网络超时。

# API查询接口

## 接口 URL

```
http://api.tmxmall.com/v1/http/translate?text=XXXX&fuzzy_threshold=0.3&user_name=XXX&client_id=d0c78b242894a032cb8c3aa7483284b4&tu_num=2&gls_num=5&de=trados
```

## 输入参数

| 参数名称 | 说明 | 参数类型 | 是否为空 |
| :---: | :---: | :---: | :---: |
| user\_name | 用户 Tmxmall 邮箱账号 | 字符 | N |
| client\_id | 用户 clientId | 字符 | N |
| de | 调用方 | 字符 | N |
| text | 检索内容 | 字符 | N |
| fuzzy\_threshold | 相似度阈值 | 字符 | N |
| tu\_num | 返回条目数量 | 字符 | N |
| gls\_num | 返回术语数量 | 字符 | N |

> 说明：client\_id 即为 tmxmall 的 API Key，client\_id 需要登录到 Tmxmall 网站个人中心查看。

## 返回参数

| 参数名称 | 含义 | 说明 |
| :---: | :---: | :---: |
| error\_code | 错误码 | 错误码，0 表示正常返回，如果使用不当或者系统出错会返回TB 打头的错误码 |
| error\_msg | 错误提示信息 | 错误提示信息 |
| from | 源语言（同参数表） | 输入句子或术语的语言编码 |
| to | 目标语言（同参数表） | 输出句子或术语的语言编码 |
| fuzzy\_threshold | 相似度阈值 | |
| tu\_num | 返回条目数量 | |
| gls\_num | 返回术语数量 | |
| text | 同参数表 | |
| fuzzy\_threshold | 同参数表 | |
| tu\_num | 同参数表 | |
| gls\_num | 同参数表 | 这里的 gls\_num 是调用API 申请时要求返回术语记录条数，若不设置，默认不返回 |
| tu\_set | 数组，语料对集合 | 这里的 tu\_num 是调用API 申请时要求返回翻译记忆记录条数，若不设置，默认返回一条 |
| gls\_set | 数组，术语对集合 | 返回术语句对的集合 |
| src | 源句子 | 源语言（from）对应的句子 |
| tgt | 目标句子 | 目标语言（to）对应的句子 |
| fuzzy | 相似度 | 请求参数 text 与翻译记忆句对源句子的相似度，基于 BLEU 算法 |

## 错误码说明

| 错误码 | 错误说明 |
| :---: | :---: |
| 0 | 查询成功 |
| TB01002 | 系统错误 |
| TB01001 | 参数不合法\(包括 clientId 不匹配\) |
| TB01021 | 用户名不存在 |
| TB02000 | 当日调用次数超过 XX 次 |

返回数据为 json 格式，示例如下：

成功：

`{"error_code":"0","error_msg":"成功","from":"zh","to":"en","text":"中国人民银行
`

`","fuzzy_threshold":0.0,"tu_num":2,"gls_num":1,"tu_set":[{"index":0,"src":"第二十一条 金银质
`

`地纪念币的铸造、发行由中国人民银行办理，其他任何单位不得铸造、仿造和发行。","tgt":"Article 21.
`

`The casting and issuing of gold and silver-based commemorative coins shall be handled by
`

`the People's Bank of China. No other units shall cast, copy or issue such
`

`items.","fuzzy":0.09053265,"user":"tmxmall tm"},{"index":0,"src":"中国合营者出资的人民币现金，
`

`需要折算成外币的，按缴款当日中国人民银行公布的基准汇率折算。","tgt":"Where the cash
`

`contribution in Renminbi made by the Chinese party needs to be converted into a foreign
`

`currency, it shall be converted at the standard exchange rate published by the People’s
`

`Bank of China on the day the payment is made.","fuzzy":0.086610675,"user":"tmxmall
`

`tm"}],"gls_set":[]}`


# API 写入接口

## 接口 URL

```
http://api.tmxmall.com/v1/http/set?seg=XXXX&tra=XXXX&user_name=XXX&client_id=d0c78b24
2894a032cb8c3aa7483284b4&de=trados&version=2015
```

## 输入 参数

| 参数名称 | 说明 | 参数类型 | 是否为空 |
| :---: | :---: | :---: | :---: |
| user\_name | 用户 Tmxmall 邮箱账号 | 字符 | N |
| client\_id | 用户 clientId | 字符 | N |
| seg | 写入数据的原文 | 字符 | N |
| tra | 写入数据的译文 | 字符 | N |
| de | 调用方 | 字符 | N |
| version | 调用方的软件版本 | 字符 | N |
| from | 源语言 | 字符 | N |
| to | 目标语言 | 字符 | N |

> 说明：client\_id 即为 tmxmall 的 API Key，client\_id 需要登录到 Tmxmall 网站个人中心查看。

## 返回参数
| 参数名称 | 含义 |说明|
| :---: | :---: |:---: |
| errCode | 错误码 |SUC表示正常返回，如果使用不当或者系统出错会返回TB打头的错误码|
| errMsg| 错误提示信息 |错误提示信息，例如：写入成功|
| status | 写入状态 |例如：success|

失败：{"errCode": "TB01001","errMsg": "该语言不合法!","status":"fail"}
成功：{"errCode": "SUC","errMsg": "写入成功!","status": "success"}

## 错误码说明
| 错误码 | 错误说明 |
| :---: | :---: |
| 0 | 私有云实时写写入成功 |
| TB01002 | 系统错误 |
| TB01001 | 参数不合法\(包括 clientId 不匹配\) |
| TB01021 | 用户名不存在 |
| TB01048 | 私有云实时写库不存在 |
| TB01049 | 私有云实时写容量已经使用完毕 |

## 其他异常情况

验证接口超时时间为 5 秒，5 秒无信息返回，接入方可认为是 Tmxmall 网站超时或者是网络超时。



