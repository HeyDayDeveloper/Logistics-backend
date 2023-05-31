# AngularJS Git Commit Message Conventions 简易说明

Angular 规范中定义的格式有 3 个内容:`Header`,`Body`,`Footer`，其中`Header`是必需的，`Body`和`Footer`可以省略

## Header

Header 部分有 3 个字段 : **type(必需)**, scope(可选), **subject(必需)**

#### type(必须)

用于说明 commit 的类别，只允许使用下面的标识

- `feat`: 新功能（feature）
- `fix/to`: 修复 bug，可以是 QA 发现的 BUG，也可以是研发自己发现的 BUG
  - `fix`: 产生 diff 并自动修复此问题。适合于一次提交直接修复问题
  - `to`: 只产生 diff 不自动修复此问题。适合于多次提交。最终修复问题提交时使用`fix`
- `docs`: 文档（documentation）
- `style`: 格式（不影响代码运行的变动）
- `refactor`: 重构（即不是新增功能，也不是修改 bug 的代码变动）
- `perf`: 优化相关，比如提升性能、体验
- `test`: 增加测试
- `chore`: 构建过程或辅助工具的变动
- `revert`: 回滚到上一个版本
- `merge`: 代码合并
- `sync`: 同步主线或分支的 Bug

#### scope(可选)

此次 commit 的影响模块

#### subject(必须)

**简短地**描述此次代码变更的主要内容

### Body

Body 部分是对本次 commit 的详细描述，可以分成多行

```text
（1）增加订单号字段
（2）增加了订单退款接口
```

日常项目开发中，如果`Header`中`subject`已经描述清楚此次代码变更的内容后，`Body`部分就可以为空

### Footer

```text
（1）不兼容变动
（2）关闭 Issue
```

日常项目中开发，Footer 不常用，可为空。
