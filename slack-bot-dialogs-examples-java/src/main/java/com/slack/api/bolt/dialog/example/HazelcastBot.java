package com.slack.api.bolt.dialog.example;

import com.hazelcast.config.Config;
import com.hazelcast.config.InterfacesConfig;
import com.hazelcast.config.NetworkConfig;
import com.slack.api.bolt.App;
import com.slack.api.bolt.dialog.context.DialogContextService;
import com.slack.api.bolt.dialog.context.HazelcastDialogContextService;
import com.slack.api.bolt.dialog.example.dialog.SimplePromptDialog;
import com.slack.api.bolt.dialog.prompt.handler.InitiatePromptDialogSlashCommandHandler;
import com.slack.api.bolt.dialog.prompt.handler.PromptDialogBoltEventHandler;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import com.slack.api.model.event.AppMentionEvent;

import java.util.List;

public class HazelcastBot {

    public static void main(String[] args) throws Exception {
        var app = new App();

        final DialogContextService dialogContextService = new HazelcastDialogContextService(
                new Config()
                        .setClusterName("simple-slack-bot")
                        .setNetworkConfig(new NetworkConfig()
                                .setInterfaces(new InterfacesConfig()
                                        .addInterface("127.0.0.1")
                                )
                        )
        );

        app.event(AppMentionEvent.class,
                new PromptDialogBoltEventHandler(
                        dialogContextService,
                        List.of(
                                new SimplePromptDialog.Listener()
                        )
                )
        );

        app.command("/test-dialog",
                new InitiatePromptDialogSlashCommandHandler(SimplePromptDialog.class, dialogContextService));

        new SocketModeApp(app).start();
    }

}
