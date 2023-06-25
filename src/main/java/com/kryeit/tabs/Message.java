package com.kryeit.tabs;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Message implements TabCompleter {
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {

        // Solo se mostrará un Tab Completion si el emisor está escribiendo el nombre de un jugador
        if(args.length==1){
            // Inicializo la lista que tendrá una lista actualizada con lo que aparecerá en el Tab Completion
            List<String> nombresPosibles = new ArrayList<>();

            // Inicializo la lista que contiene todos los nombres de los jugadores
            List<String> todosLosNombres = new ArrayList<>();

            // Meto dentro de "todosLosNombres" todos los nombres de los jugadores conectados
            Bukkit.getOnlinePlayers().forEach(p -> todosLosNombres.add(p.getName()));

            // Añado en "nombresPosibles" los nombres posibles que estén en "todosLosNombres" y que empiecen igual que lo que el emisor escribe
            for (String allTab : todosLosNombres) {
                if (allTab.toLowerCase().startsWith(args[0].toLowerCase())) {
                    nombresPosibles.add(allTab);
                }
            }

            // Devuelvo la lista con todos los nombres posibles que el emisor valla a escribir
            return nombresPosibles;
        }

        // Si no está escribiendo el nombre del receptor (es decir, está escribiendo el mensaje que va a enviar), devuelvo una lista vacía ya que, por defecto, Spigot devuelve una lista con todos los jugadores conectados si se devuelve un "null" en vez de una lista vacía
        return new ArrayList<>();
    }
}
