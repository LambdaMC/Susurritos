package com.kryeit;

import io.github.thatsmusic99.configurationmaster.CMFile;
import com.kryeit.tabs.Message;
import com.kryeit.tabs.Reply;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

// Esta es la "class" principal (Main class), el programa troncal.
public class Susurritos extends JavaPlugin {
    
    // Acceso a "plugin.yml" para obtener datos como el nombre del plugin o la versión
    PluginDescriptionFile pdffile = getDescription();
    public String name = ChatColor.YELLOW+"["+ChatColor.WHITE+pdffile.getName()+ChatColor.YELLOW+"]";
    public String version = pdffile.getVersion();

    // Para tener una instancia de la clase principal donde sea
    private static Susurritos instance;

    // Aquí se guarda la información necesaria para que /responder funcione
    public HashMap<String,String> infoResponder;

    public void onEnable(){

        // Inicializamos un nuevo hashmap para la información del /responder
        infoResponder = new HashMap<>();

        // Para la config.yml
        loadConfig();

        // Para la messages.yml
        loadMessages();

        // Inicializo la instancia
        instance = this;

        // Para registrar los comandos y los Tab Completers
        registrarComandos();

        Bukkit.getConsoleSender().sendMessage(name+ChatColor.GRAY+" El plugin ha sido activado. Version: "+ChatColor.GREEN+version);
    }

    // Cuando se apaga el servidor (O el plugin se apaga)
    public void onDisable(){
        Bukkit.getConsoleSender().sendMessage(name+ChatColor.WHITE+" El plugin ha sido desactivado.");
    }

    // Inicializo los comandos al cargar el plugin cuando se enciende el servidor
    public void registrarComandos() {
        Objects.requireNonNull(getCommand("m")).setExecutor(new com.kryeit.commands.Message(this));
        Objects.requireNonNull(getCommand("m")).setTabCompleter(new Message());
        Objects.requireNonNull(getCommand("r")).setExecutor(new com.kryeit.commands.Reply());
        Objects.requireNonNull(getCommand("r")).setTabCompleter(new Reply());
    }

    // Para acceder a la instancia desde cualquier sitio
    public static Susurritos getInstance() {
        return instance;
    }

    void loadConfig (){
        CMFile myConfigFile = new CMFile(this, "config") {
            @Override
            public void loadDefaults() {
                addLink("GitHub","https://github.com/muriplz/ComandoMensaje");

                addComment("¿Quieres sonido cuando recives un mensaje? Elegir:(true / false)");
                addDefault("sound-enabled", false);

                addComment("Web para ver las opciones: https://www.spigotmc.org/wiki/cc-sounds-list/");
                addDefault("choose-sound","BLOCK_ANVIL_FALL");

                addComment("¿Quieres que los mensajes enviados entre jugadores acaben en punto? \".\" Eligir: (true / false)");
                addDefault("dot-end-whisper",false);

                addComment("¿Quieres que se pueda mandar un mensaje a ti mismo? Elegir: (true / false)");
                addDefault("msg-yourself",true);

                addComment("¿Quieres que la información se borre al /responder? (una vez uses /responder, te tendrán\nque volver a susurrar para poder volver a responder) Elegir: (true / false)");
                addDefault("delete-respond-info",false);

                addComment("Esta opcion te permite ver tu propio mensaje cuando susurras a alguien. Elegir: (true / false)");
                addDefault("see-your-own-msg",true);
            }

        };
        myConfigFile.load();
    }

    // Para poder acceder a la messages.yml desde cualquier clase
    public static YamlConfiguration getMessages(){
        File messages = new File(getInstance().getDataFolder(), "messages.yml");
        return YamlConfiguration.loadConfiguration(messages);
    }

    void loadMessages (){
        CMFile myMessagesFile = new CMFile(this,"messages") {
            @Override
            public void loadDefaults() {
                addLink("GitHub","https://github.com/LambdaMC/Susurritos");

                addComment("\"& + Notación hexadecimal\" para usar colores.");
                addComment("Cuando el jugador usa únicamente \"/mensaje\".");
                addDefault("msg-usage","Usa /mensaje <jugador> <mensaje>.");

                addComment("Cuando el jugador no existe o no está conectado.");
                addDefault("player-not-found","&cJugador no encontrado.");

                addComment("Cuando el jugador usa únicamente \"/mensaje <Jugador>\".");
                addDefault("no-message-written","&cNo hay ningún mensaje para mandar.");

                addComment("Cuando intentas mandarte un mensaje a ti mismo.");
                addDefault("cant-msg-yoursef","No te puedes mandar mensajes a ti mismo.");

                addComment("Cuando el jugador usa /responder y no tiene a quien responder.");
                addDefault("none-to-respond","&cNo tienes a nadie a quien responder.");

                addComment("Esta es la primera parte del mensaje que ves cuando susurras a alguien.");
                addDefault("you-whispered-to","&dLe has susurrado a %PLAYER_NAME%:%WHISPER%");

                addComment("Esta es la primera parte del mensaje que ves cuando alguien te susurra.");
                addDefault("someone-whispered-you","&d%PLAYER_NAME% te susurra:%WHISPER%");

                addComment("Esto sale cuando no tienes permiso para ejecutar dicho comando.");
                addDefault("no-permission","&cNo tienes permiso para ejecutar este comando.");
            }
        };
        myMessagesFile.load();
    }


}
