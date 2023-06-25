package com.kryeit;

import net.lapismc.afkplus.playerdata.AFKPlusPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Utils {

    public static Susurritos instance = Susurritos.getInstance();
    // Una función que utiliza el sistema hexadecimal para usar la función ChatColor.
    public static String color(String mensaje){
        return ChatColor.translateAlternateColorCodes('&',mensaje);
    }

    // Una función para acceder al archivo messages.yml
    public static String getMessage(String path){
        return color(Susurritos.getMessages().getString(path));
    }

    public static void susurrar(Player emisor, Player receptor, String mensaje){
        if(instance.getConfig().getBoolean("see-your-own-msg")){
            String paraMandar = Utils.getMessage("you-whispered-to").replace("%PLAYER_NAME%", receptor.getName()).replace("%WHISPER%", mensaje);
            if(instance.getConfig().getBoolean("dot-end-whisper")){
                paraMandar = paraMandar.concat(".");
            }
            // Mensaje para el que ejecuta el comando /mensaje <Jugador> "mensaje enviado"
            emisor.sendMessage(paraMandar);
        }
        String paraRecivir = Utils.getMessage("someone-whispered-you").replace("%PLAYER_NAME%", emisor.getName()).replace("%WHISPER%", mensaje);
        if(instance.getConfig().getBoolean("dot-end-whisper")){
            paraRecivir = paraRecivir.concat(".");
        }

        // Mensaje para el jugador dentro de args[0]; es decir, el receptor del mensaje
        receptor.sendMessage(paraRecivir);

        if(instance.getConfig().getBoolean("sound-enabled")){
            String soundS = instance.getConfig().getString("choose-sound");
            Sound sound = Sound.valueOf(soundS);
            receptor.playSound(receptor.getLocation(),sound,1f,1f);
        }

        // Guardo la información para el comando /responder (Hecha de manera eficiente para que cada receptor tenga únicamente un "Player" al que responder, no varios)
        instance.infoResponder.put(receptor.getUniqueId().toString(), emisor.getUniqueId().toString());
    }
 }
