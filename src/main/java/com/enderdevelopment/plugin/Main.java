package com.enderdevelopment.plugin;

import java.util.Objects;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {
    private NamespacedKey axeKey;

    @Override
    public void onEnable() {
        axeKey = new NamespacedKey(this, "bedrock_axe");
        getServer().getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(getCommand("ascia")).setExecutor((sender, command, label, args) -> {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(Component.text("Only players can use this command.", NamedTextColor.RED));
                return true;
            }
            player.getInventory().addItem(createAxe());
            player.sendMessage(Component.text("You received the Bedrock Axe.", NamedTextColor.GREEN));
            return true;
        });
    }

    @Override
    public void onDisable() {
    }

    private ItemStack createAxe() {
        ItemStack axe = new ItemStack(Material.DIAMOND_AXE);
        ItemMeta meta = axe.getItemMeta();
        meta.displayName(Component.text("Bedrock Axe", NamedTextColor.AQUA));
        meta.lore(java.util.List.of(
                Component.text("Damage: 2000", NamedTextColor.RED),
                Component.text("Breaks bedrock, water and lava", NamedTextColor.GRAY)));
        meta.getPersistentDataContainer().set(axeKey, PersistentDataType.BYTE, (byte) 1);
        axe.setItemMeta(meta);
        return axe;
    }

    private boolean isBedrockAxe(ItemStack item) {
        if (item == null || item.getType() != Material.DIAMOND_AXE || !item.hasItemMeta()) {
            return false;
        }
        Byte marker = item.getItemMeta().getPersistentDataContainer().get(axeKey, PersistentDataType.BYTE);
        return marker != null && marker == 1;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player && isBedrockAxe(player.getInventory().getItemInMainHand())) {
            event.setDamage(2000.0);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (isBedrockAxe(event.getPlayer().getInventory().getItemInMainHand())
                && event.getBlock().getType() == Material.BEDROCK) {
            event.setDropItems(false);
        }
    }

    @EventHandler
    public void onFluidBreak(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK
                || !isBedrockAxe(event.getPlayer().getInventory().getItemInMainHand())) {
            return;
        }
        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }
        Material type = block.getType();
        if (type == Material.BEDROCK || type == Material.WATER || type == Material.LAVA) {
            block.setType(Material.AIR, true);
            event.setCancelled(true);
        }
    }
}
