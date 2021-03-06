## Naomi Progress Log

### 第一周 负责后端项目（目前）

### 第二周 待定

### 第三周 待定

### 第四周 待定

---

### 7 月 7 日

1. 大家一起讨论了数据库的大体设计，生成了最初的草图,将数据大致划分成如下实体类：（详情见 ppt 第七页）
2. 最终在实现的时候的实体类（详情见 ppt 第八页）
   后端用 spring 作为开发工具，由于目前还没有 Amazon 服务器，因此先是创建了不支持 Amazon 服务器的后端项目，计划在获得 Amazon 服务器后再将项目移植过去。
3. 在设计好实体类，我们对前后端传输的 API 逻辑进行确定，确定后确定下来的逻辑：
   - parent link: /{entityName}
   - Update: /update{var1}{var2}{var3}...
   - Add: /add{var1}{var2}{var3}...
   - Delete: /delete{Id}
   - Delete: /delete/all
   - Get: /get{Id}
   - Get: /get/all

### 目前进展： 后端实体类与 repository 已经完成，准备完善 API 的部分。

### 明天计划：

- 实现后端，且能与运维前端进行数据传输。
- 将游戏目前已有的数据存入后端。
- 在上一步完成后开始调试游戏的数据传输功能。

---

### 7 月 8 日

1. 后端全部建设完毕且已经能够运行。

### 目前进展： 后端实现

### 明天计划：

- 调试后端逻辑可能出现的 bug
- 学习 AWS 并尝试移植项目到之前创建的 AWS 项目
- 完善数据库的设计（游戏道具组合贩卖，个人游戏进度记录等）

---

### 7 月 9 日

1. 调试了后端，更新了一些 bug。
2. 创建了 AWS 服务器并用 ssh 对服务器进行连接。

目前进展： 由于部署后端到服务器后每对其进行一次调试就要对重新打包一次 jar 文件传到服务器，我们决定先提高后端的完成度，在保证不会做大改动后再部署后端。这几天可能会从客户端（app）的角度去调整后端程序。

明天计划：

- 从客户端的需求角度修改后端

---

### 7 月 10 日

1. 从客户端将抽卡函数移植到后端进行运算
2. 新建了任务实体集完善后端

目前进展： 目前运维与后端都具有一定的完成度，因此第二周可能会比较注重客户端的开发。

下周计划：详情请见小组的下周计划报告。

---

### 7 月 13 日

1. 学习了如何用 android studio 设计 ui
2. 设计了游戏内邮箱跟公告的 ui。
3. 进一步探讨了游戏机制，并初步确定了玩法。

目前进展： 今天还计划了这一周的工作计划，总体上中心会比较偏向客户端的开发。

明天计划： 继续设计 ui，并争取完成全部 ui 设计。

---

### 7 月 14 日

1. 完成了背包部分的 layout
2. 研究了 Android Studio 中 Activity 之间的关系。

目前进展：ui 已经几乎项目中的 layout 跳转可能将需要同时进行 activity 跳转，明天可能要集中讨论一下。后端数据也需要在单元测试前进行修改。

明天计划： 完成后端（至少大部分）的修改，完成 bagSection 的 activity 跳转逻辑。

---

### 7 月 15 日

1. 修改了后端
2. activity 之间的跳转逻辑已完成（izumi）

目前进展： 目前需要先把后端跟前端运维的单元测试补上，然后就是游戏的移植需要加快进度。

明天计划： 完成（至少大部分的）单元测试。

---

### 7 月 16 日

1. 做了 UnitController 的单元测试

目前进展： 搭建 debug 环境花了一定时间后发现单元测试的调试花了更长的时间，目前是已经完成了单元测试但是还是很有不通过的测试。

明天计划： 还是先把测试搭建起来为主，调试可以放在后面。

---

### 7 月 17 日

1. 完成 controller 层的单元测试
2. 修复了一些后端的 bug

目前进展： 代码覆盖率的检测道具因为未知原因崩坏，需要进行修复。

下周计划： 完成 service + dao 层的单元测试，修复代码覆盖率检测道具。

---

### 7 月 20 日

1. 做了一部分已使用功能的 serviceTest（单元测试）
2. 修改了 ownCard,ownItem 重复拥有时的逻辑（e2e）
3. 修改了登录逻辑的一些 bug（e2e）

目前进展： 要加入用户编队的信息及其 API

本周计划： 单元测试已经大部分完成，现在需要对后端进行完善，完成 e2e 和 aws 部署。

---

### 7 月 21 日

1. e2e 的测试目前主要在客户端与前端进行检测。
2. 学习了 WebSecurity 如何 Implement。
3. 目前已经将 WebSecurity 添加到项目中，但需要进行调试。

目前进展： WebSecurity 需要进一步调试来正确运行

明天计划： 将 webSecurity 调试完成。

---

### 7 月 22 日

1. 完成了 webSecurity 后端的调试

目前进展： User 的登陆 API 限制做好了，现在是需要做两个不同的 configuration class 来分开 admin 跟 user 的权限

明天计划： 实现 admin 跟 user 的不同 API authorization。

---

### 7 月 23 日

1. 增加了 multiHttpConfig 来完成对不同 entity 的身份判定

目前进展：想要实现用不同的 webConfig 去给予不同用户群体权限

明天计划：实现 multiHttpConfig

---

### 7 月 24 日

1. 换用 hasRole + PreAuthorization 的方式实现身份判定

目前进展： 不同身份用户登录+注册验证机制已完成

下周计划暂无

周末计划： 完善后端 api 接口

---

### 7 月 27 日

今天要完成：

主要有：公布/邮箱的查看、卡牌的强化

功能：

1. 邮箱

   - 获取用户邮箱内所有邮件
   - 给指定邮箱发送邮件（单个用户+全体用户）

2. 活动（公告）

   - 获取所有公告
   - 发布公告

3. 抽卡选取逻辑

   - 计算出本次抽卡稀有度多少（逻辑已有）

   - 计算出本次抽卡得到哪个派系（逻辑已有）

   - 查询本游戏已经有的卡牌，该稀有度该派系的卡牌总共有多少个

   - 随机抽取其中一张

4. 后端：增加卡牌的剩余强化点数属性

---

### 7 月 28 日

今天完成： 更新单元测试， 卡牌升级逻辑（ 强化点数 ， 升级逻辑 ）

明天完成： AWS 部署

---

### 7 月 29 日 & 30 日

进展： 完成基本单元测试，服务器已安装 mysql 和 mongodb

明天计划：jar 文件传输+java 安装完成服务器部署

1. AWS 部署

- 服务器属性：

  Operating System: Ubuntu 16.04.6 LTS

  Kernel: Linux 4.4.0-1109-aws

  Architecture: x86-64

  root-password:SECARDGAME

* [服务器部署 Document](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/managing-users.html)

* [服务器部署 Dashboard](https://console.aws.amazon.com/ec2/v2/home?region=us-east-1#SecurityGroups:)

* [AWS Educate](https://www.awseducate.com/student/s/awssite)

* [服务器安装 mysql](https://www.linuxidc.com/Linux/2017-06/144805.htm)

- [安装 mongodb](https://blog.csdn.net/zn505119020/article/details/81331808)

  启动 mongodb：

      cd /usr/local/mongodb/bin

      ./mongod -f mongodb.conf

      ./mongo

AWS 账号密码：

       email: edward.raymond.he@sjtu.edu.cn

      pswd: S1project!

AWS 目前开放了 8080 端口（测试用），后期可能会限制 inbound rules

服务器后端项目访问地址（共有 Ip）： http://ec2-35-173-219-114.compute-1.amazonaws.com:8080

### 7 月 30 日

今天完成： AWS 部署

明天完成： 数据插入+并发性测试

---

### 7 月 31 日

今天完成：AWS 部署后的衔接测试

## 后五周任务

由于后端基本上都是需要配合运维与客户端进行工作的，因此下面就列出需要完成的工作，不进行具体的时间安排。

### 后端完善：

- Issues 上列举的问题进行修改完善

- Api Pagination 的实现

- 用户名 HASH 后再存进数据库中（写 HASH 逻辑与解析逻辑），跟目前的登录逻辑进行结合

- 限制用户只能修改自己的相关信息（逻辑）

- AutoScaling 的具体实现

- 单元测试（用 service 取代目前 Controller 层的单元测试）

- 其他游戏逻辑的移植

---

### 8 月 4 日 更新

- 完成 API Pagination
