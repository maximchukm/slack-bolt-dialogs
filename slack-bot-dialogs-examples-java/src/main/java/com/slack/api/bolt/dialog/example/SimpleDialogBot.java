package com.slack.api.bolt.dialog.example;

import com.slack.api.bolt.App;
import com.slack.api.bolt.dialog.context.DefaultDialogContextService;
import com.slack.api.bolt.dialog.context.DialogContextService;
import com.slack.api.bolt.dialog.example.dialog.SimplePromptDialog;
import com.slack.api.bolt.dialog.prompt.handler.InitiatePromptDialogSlashCommandHandler;
import com.slack.api.bolt.dialog.prompt.handler.PromptDialogBoltEventHandler;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import com.slack.api.model.event.AppMentionEvent;

import java.util.List;

public class SimpleDialogBot {

    public static void main(String[] args) throws Exception {
        var app = new App();

        final DialogContextService dialogContextService = new DefaultDialogContextService();

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
