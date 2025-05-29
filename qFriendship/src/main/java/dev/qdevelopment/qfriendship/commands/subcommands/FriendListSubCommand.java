package dev.qdevelopment.qfriendship.commands.subcommands;

import dev.qdevelopment.qfriendship.Plugin;
import dev.qdevelopment.qfriendship.commands.impl.SubCommand;
import dev.qdevelopment.qfriendship.entity.Friend;
import dev.qdevelopment.qfriendship.utils.ComponentUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FriendListSubCommand extends SubCommand {
    @Override
    public String getArgument() {
        return "list";
    }

    @Override
    public void command(ProxiedPlayer player, String[] args) {
        if (args.length == 2) {
            String number = args[1];
            try {
                int pageNum = Integer.parseInt(number);
                sendPage(player, pageNum);
            } catch (NumberFormatException e) {
                // Handle invalid number input if needed
            }
            return;
        }

        sendPage(player, 1);
    }

    public void sendPage(ProxiedPlayer player, int page) {
        init(player);

        List<Friend> allFriends = Plugin.getInstance().getMainCache().get(player.getUniqueId().toString());
        if (allFriends == null) {
            // Handle missing data gracefully
            Plugin.getInstance().getAudiences().player(player).sendMessage(
                    Component.text("No friends found.", NamedTextColor.RED)
            );
            return;
        }

        int friendsAmount = allFriends.size();
        int pages = (friendsAmount + 8 - 1) / 8;

        List<Friend> pagesFriends = Plugin.getInstance().getFriendsList().get(player).get(page);
        if (pagesFriends == null) {
            Plugin.getInstance().getAudiences().player(player).sendMessage(
                    Component.text("That page doesn't exist.", NamedTextColor.RED)
            );
            return;
        }

        Component mainComponent = Component.text("─────────────────────────────────", NamedTextColor.DARK_GRAY).appendNewline().appendNewline()
                .append(Component.text("Friends Page (" + page + " of " + pages + ")", NamedTextColor.GRAY)).appendNewline().appendNewline();

        for (Friend friend : pagesFriends) {
            mainComponent = mainComponent.append(ComponentUtils.friendComponent(friend)).appendNewline();
        }

        mainComponent = mainComponent.append(Component.text("─────────────────────────────────", NamedTextColor.DARK_GRAY)).appendNewline();
        mainComponent = mainComponent.append(ComponentUtils.previousPage(player))
                .append(Component.text(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ "))
                .append(ComponentUtils.nextPage(player));

        Plugin.getInstance().getAudiences().player(player).sendMessage(mainComponent);
    }

    public void init(ProxiedPlayer player) {
        Plugin.getInstance().getFriendsList().remove(player);

        List<Friend> totalFriends = Plugin.getInstance().getMainCache().get(player.getUniqueId().toString());
        if (totalFriends == null) {
            totalFriends = new ArrayList<>();
        }

        int pages = (totalFriends.size() + 8 - 1) / 8;

        HashMap<Integer, List<Friend>> map = new HashMap<>();
        for (int i = 1; i <= pages; i++) {
            map.put(i, new ArrayList<>());
        }

        for (int i = 0; i < totalFriends.size(); i++) {
            int page = (i / 8) + 1;
            map.get(page).add(totalFriends.get(i));
        }

        Plugin.getInstance().getFriendsList().put(player, map);
    }
}

