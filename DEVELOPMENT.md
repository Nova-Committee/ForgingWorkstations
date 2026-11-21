# 开发与验证

Java 25 / Minecraft 26.1.2 / NeoForge，版本以 `gradle.properties` 为准。

本附属为独立 Gradle 工程，通过 `build.gradle` 中的 Curse Maven 坐标引用 MiXianTu 发行包，不导入本体源码工程，也不要求两个仓库位于相邻目录。发布 Jar 只包含本附属主代码与资源，不内嵌本体或测试包。Saturn、ApricityUI 和 Jupiter 作为独立运行依赖显式声明，不依赖 Curse Maven 提供本体的传递依赖。

本体继续使用远程 Curse Maven 坐标，共享测试包放在本工程的 `libs/mxt-test-mod.jar`，通过 `testModImplementation` 文件依赖加入测试源码集，普通运行配置不加载该 Jar。测试包来自 [MiXianTu Test Module](https://www.curseforge.com/minecraft/mc-mods/mxt/files/9729564)，更新时下载匹配本体的测试包并替换该文件。文件依赖不参与 Maven 版本仲裁，避免同名模块的测试包替换本体；也不进入主代码编译依赖、发布 POM 或附属 Jar。无需本地 Maven，不要将本体作为附属的 Gradle 子工程链接。

- 编译：`gradlew.bat compileJava compileTestModJava --console=plain`
- 刷新测试资源：`gradlew.bat processTestModResources --console=plain`
- 打包：`gradlew.bat build --console=plain`
- 普通客户端：`gradlew.bat runClient --console=plain`，仅加载附属主模组与正式依赖，不加载任何测试包。
- Test Client（先征求确认）：`gradlew.bat runTestClient --console=plain`，加载本体共享测试包与附属测试模组，独立使用 `run-test-client/`。IDE 重新加载 Gradle 后可选择 `Test Client`。可用 `-PmxtTestClientGameDirectory=<目录>` 与 `-PmxtTestClientUsername=<名称>` 覆盖游戏目录与玩家名。
- GameTest（先征求确认）：`gradlew.bat runGameTestServer --console=plain`，默认只选 `mxt_forging_test:*`；用 `-PmxtGameTestSelection=<选择器>` 修改。

普通 Client、Server 与 Data 不加载测试模组，也不执行测试资源的编译与复制。Test Client 与专用 GameTest Server 使用测试源码集，只在这两类入口加载测试包；GameTest 的游戏目录为 `run-game-test-server/`。

`src/test-mod` 为独立测试模组。探针复用本体测试包的 Probe 框架与公共夹具；工作站专用夹具随本附属维护，不向本体反向添加依赖。测试模组与真实附属使用不同 mod id。

沿用本体协作规则：不擅自提交、不覆盖他人改动；启动客户端、服务端或 GameTest 前确认；Java 注释英文，研究正文中文，文档不写日期或固定模组版本。

三个附属各自独立导入与运行，不自动载入其他附属工程，也不要求它们位于相邻目录。

注册归属：自有注册项、资源与测试工艺表使用 `mxt_forging` 命名空间，旧 `mxt:*` ID 不兼容；测试定义的条目 ID 仍可使用 `mxt_test:*`。共享热源 `mxt:heat_source`、共享物品及 `mxt:workstations` 标签留在本体。
