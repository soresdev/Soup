package me.sores.soup.listener;

import me.sores.Orion.util.entity.CopyOfFishingHook;
import me.sores.Orion.util.moreprojectiles.event.BlockProjectileHitEvent;
import me.sores.Orion.util.moreprojectiles.event.ItemProjectileHitEvent;
import me.sores.soup.ability.Ability;
import me.sores.soup.ability.wrapper.*;
import me.sores.soup.handler.ProfileHandler;
import me.sores.soup.kit.Kit;
import me.sores.soup.profile.SoupProfile;
import me.sores.soup.util.SoupUtil;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by sores on 8/11/2022.
 */
public class AbilityListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        SoupProfile profile = ProfileHandler.getInstance().getProfile(player.getUniqueId());

        if(profile.getSelectedKit() != null){
            Kit kit = profile.getSelectedKit();

            if(kit.hasAbility()){
                Ability ability = kit.getAbility();
                if(ability.fetchWrapperItems().contains(IInteract.class)){
                    ((IInteract) ability).onPlayerInteract(kit, event);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        SoupProfile profile = ProfileHandler.getInstance().getProfile(player.getUniqueId());

        if(profile.hasSelectedKit()){
            Kit kit = profile.getSelectedKit();

            if(kit.hasAbility()){
                Ability ability = kit.getAbility();

                if(ability.fetchWrapperItems().contains(IDeath.class)){
                    ((IDeath) ability).onPlayerDeath(kit, event);
                }
            }
        }

        if(player.getKiller() != null){
            Player killer = player.getKiller();
            SoupProfile killerProfile = ProfileHandler.getInstance().getProfile(killer.getUniqueId());

            if(killerProfile.hasSelectedKit()){
                Kit kit = killerProfile.getSelectedKit();

                if(kit.hasAbility()){
                    Ability ability = kit.getAbility();

                    if(ability.fetchWrapperItems().contains(IKillGained.class)){
                        ((IKillGained) ability).onKillGained(kit, event, player, killer);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        SoupProfile profile = ProfileHandler.getInstance().getProfile(player.getUniqueId());

        if(profile.hasSelectedKit()){
            Kit kit = profile.getSelectedKit();

            if(kit.hasAbility()){
                Ability ability = kit.getAbility();

                if(ability.fetchWrapperItems().contains(IBlockPlace.class)){
                    ((IBlockPlace) ability).onPlayerBlockPlace(kit, event);
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        SoupProfile profile = ProfileHandler.getInstance().getProfile(player.getUniqueId());

        if(profile.hasSelectedKit()){
            Kit kit = profile.getSelectedKit();

            if(kit.hasAbility()){
                Ability ability = kit.getAbility();

                if(ability.fetchWrapperItems().contains(IBlockBreak.class)){
                    ((IBlockBreak) ability).onPlayerBlockBreakEvent(kit, event);
                }
            }
        }
    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event){
        if(event.getPotion().getShooter() != null && event.getPotion().getShooter() instanceof Player){
            Player player = (Player) event.getPotion().getShooter();
            SoupProfile profile = ProfileHandler.getInstance().getProfile(player.getUniqueId());

            if(profile.hasSelectedKit()){
                Kit kit = profile.getSelectedKit();

                if(kit.hasAbility()){
                    Ability ability = kit.getAbility();

                    if(ability.fetchWrapperItems().contains(IPotionSplashEvent.class)){
                        ((IPotionSplashEvent) ability).onPotionSplash(kit, event);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onItemProjectileHit(ItemProjectileHitEvent event){
        if(event.isCancelled()) return;

        if(event.getHitBlock() != null){
            Player shooter;

            if(event.getProjectile() != null){
                if(event.getProjectile().getShooter() != null && event.getProjectile().getShooter() instanceof Player){
                    shooter = (Player) event.getProjectile().getShooter();

                    if(shooter != null){
                        SoupProfile profile = ProfileHandler.getInstance().getProfile(shooter);

                        if(profile.getSelectedKit() != null){
                            Kit kit = profile.getSelectedKit();

                            if(kit.hasAbility()){
                                Ability ability = kit.getAbility();

                                if(ability.fetchWrapperItems().contains(IItemProjectileHitBlock.class)){
                                    ((IItemProjectileHitBlock) ability).onItemProjectileHitBlock(shooter, event);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }

        if(event.getHitEntity() != null && event.getHitEntity() instanceof Player){
            Player shooter;

            if(event.getProjectile() != null){
                if(event.getProjectile().getShooter() != null && event.getProjectile().getShooter() instanceof Player){
                    shooter = (Player) event.getProjectile().getShooter();

                    if(shooter != null){
                        SoupProfile profile = ProfileHandler.getInstance().getProfile(shooter);

                        if(profile.getSelectedKit() != null){
                            Kit kit = profile.getSelectedKit();

                            if(kit.hasAbility()){
                                Ability ability = kit.getAbility();

                                if(ability.fetchWrapperItems().contains(IItemProjectileHit.class)){
                                    ((IItemProjectileHit) ability).onItemProjectileHit(shooter, (Player) event.getHitEntity(), event);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockProjectileHit(BlockProjectileHitEvent event){
        if(event.isCancelled()) return;

        if(event.getHitBlock() != null){
            Player shooter;

            if(event.getProjectile() != null){
                if(event.getProjectile().getShooter() != null && event.getProjectile().getShooter() instanceof Player){
                    shooter = (Player) event.getProjectile().getShooter();

                    if(shooter != null){
                        SoupProfile profile = ProfileHandler.getInstance().getProfile(shooter);

                        if(profile.getSelectedKit() != null){
                            Kit kit = profile.getSelectedKit();

                            if(kit.hasAbility()){
                                Ability ability = kit.getAbility();

                                if(ability.fetchWrapperItems().contains(IBlockProjetileHitBlock.class)){
                                    ((IBlockProjetileHitBlock) ability).onBlockPrjectileHitBlock(shooter, event);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }

        if(event.getHitEntity() != null && event.getHitEntity() instanceof Player){
            Player shooter;

            if(event.getProjectile() != null){
                if(event.getProjectile().getShooter() != null && event.getProjectile().getShooter() instanceof Player){
                    shooter = (Player) event.getProjectile().getShooter();

                    if(shooter != null){
                        SoupProfile profile = ProfileHandler.getInstance().getProfile(shooter);

                        if(profile.getSelectedKit() != null){
                            Kit kit = profile.getSelectedKit();

                            if(kit.hasAbility()){
                                Ability ability = kit.getAbility();

                                if(ability.fetchWrapperItems().contains(IBlockProjectileHit.class)){
                                    ((IBlockProjectileHit) ability).onBlockProjectileHit(shooter, (Player) event.getHitEntity(), event);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof CopyOfFishingHook){
            event.setCancelled(true);
            return;
        }

        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            SoupProfile profile = ProfileHandler.getInstance().getProfile(player.getUniqueId());

            if(profile.getSelectedKit() != null){
                Kit kit = profile.getSelectedKit();

                if(kit.hasAbility()){
                    Ability ability = kit.getAbility();
                    if(ability.fetchWrapperItems().contains(IPlayerDamageByEntity.class)){
                        ((IPlayerDamageByEntity) ability).onPlayerDamageByEntity(kit, event, player, event.getDamager());
                    }
                }
            }
        }

        if(event.getDamager() instanceof Player){
            Player damager = (Player) event.getDamager();
            SoupProfile damagerProfile = ProfileHandler.getInstance().getProfile(damager.getUniqueId());

            if(damagerProfile.getSelectedKit() != null){
                Kit kit = damagerProfile.getSelectedKit();

                if(kit.hasAbility()){
                    Ability ability = kit.getAbility();
                    if(ability.fetchWrapperItems().contains(IEntityDamageByPlayer.class)){
                        ((IEntityDamageByPlayer) ability).onEntityDamageByPlayer(kit, event, event.getEntity(), damager);
                    }
                }
            }
        }else if(event.getDamager() instanceof Projectile){
            if(((Projectile) event.getDamager()).getShooter() != null && ((Projectile) event.getDamager()).getShooter() instanceof Player){
                Player shooter = (Player) ((Projectile) event.getDamager()).getShooter();
                SoupProfile shooterProfile = ProfileHandler.getInstance().getProfile(shooter.getUniqueId());

                if(shooterProfile.getSelectedKit() != null){
                    Kit kit = shooterProfile.getSelectedKit();

                    if(kit.hasAbility()){
                        Ability ability = kit.getAbility();
                        if(ability.fetchWrapperItems().contains(IPlayerHitEntityWithProjectile.class)){
                            ((IPlayerHitEntityWithProjectile) ability).onPlayerHitEntityWithProjectile(kit, event, (Projectile) event.getDamager(), shooter, event.getEntity());
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event){
        if(event.getEntity() != null && event.getEntity().getShooter() != null && event.getEntity().getShooter() instanceof Player){
            Player shooter = (Player) event.getEntity().getShooter();
            SoupProfile profile = ProfileHandler.getInstance().getProfile(shooter.getUniqueId());

            if(profile.getSelectedKit() != null){
                Kit kit = profile.getSelectedKit();

                if(kit.hasAbility()){
                    Ability ability = kit.getAbility();
                    if(ability.fetchWrapperItems().contains(IPlayerProjectileHitEvent.class)){
                        ((IPlayerProjectileHitEvent) ability).onPlayerProjectileHitEvent(kit, event);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event){
        Player player = event.getPlayer();
        SoupProfile profile = ProfileHandler.getInstance().getProfile(player.getUniqueId());
        if(player.isDead()) return;

        if(profile.getSelectedKit() != null){
            Kit kit = profile.getSelectedKit();

            if(kit.hasAbility()){
                Ability ability = kit.getAbility();
                if(ability.fetchWrapperItems().contains(IToggleFlight.class)){
                    ((IToggleFlight) ability).onPlayerToggleFlight(kit, event);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerHealthRegain(EntityRegainHealthEvent event){
        if(event.getEntity().isDead() || !(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        SoupProfile profile = ProfileHandler.getInstance().getProfile(player.getUniqueId());

        if(profile.getSelectedKit() != null){
            Kit kit = profile.getSelectedKit();

            if(kit.hasAbility()){
                Ability ability = kit.getAbility();
                if(ability.fetchWrapperItems().contains(IPlayerHealthRegain.class)){
                    ((IPlayerHealthRegain) ability).onPlayerHealthRegen(kit, event);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerUnmount(VehicleExitEvent event){
        if(event.getExited() instanceof Player){
            Player player = (Player) event.getExited();
            SoupProfile profile = ProfileHandler.getInstance().getProfile(player.getUniqueId());

            if(profile.getSelectedKit() != null){
                Kit kit = profile.getSelectedKit();

                if(kit.hasAbility()){
                    Ability ability = kit.getAbility();
                    if(ability.fetchWrapperItems().contains(IPlayerUnmount.class)){
                        ((IPlayerUnmount) ability).onPlayerUnmount(kit, event);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event){
        Player player = event.getPlayer();
        SoupProfile profile = ProfileHandler.getInstance().getProfile(player.getUniqueId());

        if(profile.getSelectedKit() != null){
            Kit kit = profile.getSelectedKit();

            if(kit.hasAbility()){
                Ability ability = kit.getAbility();
                if(ability.fetchWrapperItems().contains(IPlayerInteractEntity.class)){
                    ((IPlayerInteractEntity) ability).onPlayerInteractEntity(kit, event);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event){
        Player player = event.getPlayer();
        SoupProfile profile = ProfileHandler.getInstance().getProfile(player.getUniqueId());

        if(profile.getSelectedKit() != null){
            Kit kit = profile.getSelectedKit();

            if(kit.hasAbility()){
                Ability ability = kit.getAbility();
                if(ability.fetchWrapperItems().contains(IFish.class)){
                    ((IFish) ability).onPlayerFish(kit, event);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerPickup(PlayerPickupItemEvent event){
        Player player = event.getPlayer();
        if(player.isDead()) return;
        SoupProfile profile = ProfileHandler.getInstance().getProfile(player.getUniqueId());

        if(profile.getSelectedKit() != null){
            Kit kit = profile.getSelectedKit();

            if(kit.hasAbility()){
                Ability ability = kit.getAbility();
                if(ability.fetchWrapperItems().contains(IPickupItem.class)){
                    ((IPickupItem) ability).onPlayerPickupItem(kit, event);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        SoupProfile profile = ProfileHandler.getInstance().getProfile(player.getUniqueId());

        if(profile.getSelectedKit() != null){
            Kit kit = profile.getSelectedKit();

            if(kit.hasAbility()){
                Ability ability = kit.getAbility();
                if(ability.fetchWrapperItems().contains(IItemDrop.class)){
                    ((IItemDrop) ability).onItemDrop(kit, event);
                }
            }
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent event){
        if(event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()){
            ItemStack item = event.getCurrentItem();
            ItemMeta meta = item.getItemMeta();

            if(meta.hasLore()){
                if(meta.getLore().contains(SoupUtil.BOUND_LORE)){
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerCraft(CraftItemEvent event){
        if(event.isCancelled() || !(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        SoupProfile profile = ProfileHandler.getInstance().getProfile(player.getUniqueId());

        if(profile.getSelectedKit() != null){
            Kit kit = profile.getSelectedKit();

            if(kit.hasAbility()){
                Ability ability = kit.getAbility();
                if(ability.fetchWrapperItems().contains(IPlayerCraft.class)){
                    ((IPlayerCraft) ability).onPlayerCraft(kit, event);
                }
            }
        }
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event){
        if(event.getEntity().getShooter() != null && event.getEntity().getShooter() instanceof Player){
            Player shooter = (Player) event.getEntity().getShooter();
            SoupProfile profile = ProfileHandler.getInstance().getProfile(shooter.getUniqueId());

            if(profile.getSelectedKit() != null){
                Kit kit = profile.getSelectedKit();

                if(kit.hasAbility()){
                    Ability ability = kit.getAbility();
                    if(ability.fetchWrapperItems().contains(IProjectileLaunch.class)){
                        ((IProjectileLaunch) ability).onProjectileLaunch(kit, event, shooter);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            SoupProfile profile = ProfileHandler.getInstance().getProfile(player.getUniqueId());

            if(profile.getSelectedKit() != null){
                Kit kit = profile.getSelectedKit();

                if(kit.hasAbility()){
                    Ability ability = kit.getAbility();
                    if(ability.fetchWrapperItems().contains(IPlayerDamage.class)){
                        ((IPlayerDamage) ability).onPlayerDamage(kit, event, player);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onItemHeldEvent(PlayerItemHeldEvent event){
        Player player = event.getPlayer();
        SoupProfile profile = ProfileHandler.getInstance().getProfile(player.getUniqueId());

        if(profile.getSelectedKit() != null){
            Kit kit = profile.getSelectedKit();

            if(kit.hasAbility()){
                Ability ability = kit.getAbility();
                if(ability.fetchWrapperItems().contains(IPlayerHeldItemEvent.class)){
                    ((IPlayerHeldItemEvent) ability).onPlayerHeldItem(kit, event, player);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerTeleportWrap(PlayerTeleportEvent event){
        Player player = event.getPlayer();
        SoupProfile profile = ProfileHandler.getInstance().getProfile(player.getUniqueId());

        if(profile.getSelectedKit() != null){
            Kit kit = profile.getSelectedKit();

            if(kit.hasAbility()){
                Ability ability = kit.getAbility();
                if(ability.fetchWrapperItems().contains(IPlayerTeleport.class)){
                    ((IPlayerTeleport) ability).onPlayerTeleport(kit, event);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event){
        Player player = event.getPlayer();
        if(player.isDead()) return;
        SoupProfile profile = ProfileHandler.getInstance().getProfile(player.getUniqueId());

        if(profile.getSelectedKit() != null){
            Kit kit = profile.getSelectedKit();

            if(kit.hasAbility()){
                Ability ability = kit.getAbility();
                if(ability.fetchWrapperItems().contains(IToggleSneak.class)){
                    ((IToggleSneak) ability).onToggleSneak(kit, event);
                }
            }
        }
    }

}
