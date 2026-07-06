package com.smarthr.smarthr.message.service.impl;

import com.smarthr.smarthr.config.TwilioProperties;
import com.smarthr.smarthr.message.dto.GeneratedMessage;
import com.smarthr.smarthr.message.dto.RecipientType;
import com.smarthr.smarthr.message.service.WhatsappService;
import com.twilio.Twilio;
import com.twilio.exception.TwilioException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TwilioWhatsappService implements WhatsappService {

    private final TwilioProperties twilioProperties;


    @Override
    public void sendToAdmin(List<GeneratedMessage> messages) {
        String message = buildAdminMessage(messages);
        sendWhatsapp(message);
    }

    private String buildAdminMessage(List<GeneratedMessage> messages) {

        StringBuilder builder = new StringBuilder();

        builder.append("📚 *New Tuition Messages*")
                .append("\n\n");

        for (GeneratedMessage message : messages) {

            builder.append("━━━━━━━━━━━━━━━━━━━━")
                    .append("\n\n");

            builder.append(getHeading(message.getRecipientType()))
                    .append("\n\n");

            builder.append(message.getMessage())
                    .append("\n\n");
        }

        return builder.toString();
    }

    //This will communicate with twilio server
    private void sendWhatsapp(String message) {
        Twilio.init(
                twilioProperties.getAccountSid(),
                twilioProperties.getAuthToken()
        );
        try {
            Message.creator(
                    new PhoneNumber(twilioProperties.getAdminNumber()),
                    new PhoneNumber(twilioProperties.getWhatsappFrom()),
                    message
            ).create();
        } catch (TwilioException e) {
            throw new RuntimeException(
                    "Failed to send WhatsApp message.",
                    e
            );
        }
    }

    private String getHeading(RecipientType recipientType) {

        return switch (recipientType) {

            case TUTOR -> "👨‍🏫 *Tutor Message*";

            case PARENT -> "👨‍👩‍👦 *Parent Message*";
        };

    }
}
