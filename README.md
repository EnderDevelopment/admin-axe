# Minecraft Admin Axe Plugin

Admin Axe is a Minecraft plugin that adds a custom diamond axe with 2000 attack damage and the ability to remove bedrock, water, and lava. The axe is identified internally by a persistent item marker, so only axes issued by the plugin receive these special abilities.

## Install

1. Install Java 21.
2. Run `mvn package` in the project directory.
3. Copy `target/plugin.jar` to your server's `plugins` folder.
4. Start or restart a Paper Minecraft server running version 1.21.11 or a compatible 1.21 version.
5. Confirm that the plugin has loaded successfully.

## Use

1. Join the server as an operator or with the `bedrockaxe.give` permission.
2. Run `/ascia`.
3. The plugin gives you the custom Bedrock Axe, which appears as a diamond axe named `Bedrock Axe`.
4. Attack an entity while holding the axe in your main hand to deal 2000 damage.
5. Left-click a bedrock, water, or lava block while holding the axe in your main hand to replace that block with air.
6. The `bedrockaxe.give` permission controls access to the `/ascia` command and is granted to operators by default.

---

More Minecraft plugin projects by EnderDevelopment: https://github.com/EnderDevelopment

Project page: https://dash.enderdevelopment.com/community/29ea9dee-0086-4a8f-bd2f-760ec1197c57?utm_source=github&utm_medium=readme&utm_campaign=auto_publish&utm_content=29ea9dee-0086-4a8f-bd2f-760ec1197c57
