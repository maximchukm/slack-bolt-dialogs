package com.slack.api.bolt.dialog.example.dialog;

import com.slack.api.bolt.context.builtin.EventContext;
import com.slack.api.bolt.dialog.Dialog;
import com.slack.api.bolt.dialog.prompt.Prompt;
import com.slack.api.bolt.dialog.prompt.PromptDialog;
import com.slack.api.bolt.dialog.prompt.listener.PromptDialogListener;
import com.slack.api.methods.SlackApiException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class SimplePromptDialog extends PromptDialog {

    private static final String NAME_PROMPT = "name";

    private static final String COLOR_PROMPT = "color";

    public SimplePromptDialog() {
        addPrompt(NAME_PROMPT, "What is your name?");
        addPrompt(COLOR_PROMPT, "What is your favorite color?");
    }

    public static class Listener implements PromptDialogListener {

        @NotNull
        @Override
        public Class<? extends Dialog> getDialogClass() {
            return SimplePromptDialog.class;
        }

        @Override
        public void onPromptCompleted(@NotNull PromptDialog promptDialog, @NotNull Prompt prompt, @NotNull EventContext eventContext) throws IOException, SlackApiException {
        }

        @Override
        public void onDialogCompleted(@NotNull Dialog dialog, @NotNull EventContext eventContext) throws IOException, SlackApiException {
            final var name = dialog.getAnswers().get(NAME_PROMPT);
            final var color = dialog.getAnswers().get(COLOR_PROMPT);

            eventContext.say(String.format("Your name is %s and your favorite color is %s", name, color));
        }
    }
}
