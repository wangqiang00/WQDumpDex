# WQDumpDex
## 简介
这是一个运行在xposed上的用于android app 脱壳的工具
## 使用说明
1. 将app安装在手机上，进入xposed进行模块勾选后重启
2. 手机重启后，点击工具在桌面的图标，进入工具页面
3. 在第一个edittext中输入要脱壳的app包名
4. 在第二个edittext中输入要脱壳的app中的一个activity类名（activity类可以使用jeb或apkide打开manifest文件中查找）
5. 启动要脱壳的app，进入配置的activity页面
6. 以上就完成了dex dump，dex写入/data/data/目标app包名/下 ***.dex
