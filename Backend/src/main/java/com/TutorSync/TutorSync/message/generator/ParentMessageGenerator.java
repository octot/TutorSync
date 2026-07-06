package com.TutorSync.TutorSync.message.generator;


import com.TutorSync.TutorSync.message.dto.GeneratedMessage;
import com.TutorSync.TutorSync.message.dto.MessageRequest;
import com.TutorSync.TutorSync.message.dto.RecipientType;
import org.springframework.stereotype.Component;

@Component
public class ParentMessageGenerator implements MessageTemplate {



    @Override
    public GeneratedMessage generate(MessageRequest request) {
        GeneratedMessage message = new GeneratedMessage();

        message.setRecipientType(RecipientType.PARENT);
        message.setRecipientName(request.getParentName());
        message.setRecipientNumber(request.getParentNumber());

        message.setMessage(buildMessage(request));

        return message;
    }
    private String buildMessage(MessageRequest request) {

        return """
                Dear %s,

                Your tuition has been scheduled.

                Time: %s - %s

                Fee: ₹%.2f

                %s
                """
                .formatted(
                        request.getParentName(),
                        request.getSchedule().getStartTime(),
                        request.getSchedule().getEndTime(),
                        request.getPaymentFromParent(),
                        request.getAdditionalMessageToParent()
                );
    }
}
