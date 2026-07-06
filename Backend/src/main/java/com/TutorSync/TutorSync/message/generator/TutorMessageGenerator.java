package com.smarthr.smarthr.message.generator;


import com.smarthr.smarthr.message.dto.GeneratedMessage;
import com.smarthr.smarthr.message.dto.MessageRequest;
import com.smarthr.smarthr.message.dto.RecipientType;
import org.springframework.stereotype.Component;

@Component
public class TutorMessageGenerator implements MessageTemplate {


    @Override
    public GeneratedMessage generate(MessageRequest request) {
        GeneratedMessage message = new GeneratedMessage();

        message.setRecipientType(RecipientType.TUTOR);
        message.setRecipientName(request.getTutorName());
        message.setRecipientNumber(request.getTutorNumber());

        message.setMessage( buildMessage(request));

        return message;
    }

    private String buildMessage(MessageRequest request) {

        return """
                Hi %s,

                Your tuition has been assigned.

                Time: %s - %s

                Payment: ₹%.2f

                %s
                """
                .formatted(
                        request.getTutorName(),
                        request.getSchedule().getStartTime(),
                        request.getSchedule().getEndTime(),
                        request.getPaymentToTutor(),
                        request.getAdditionalMessageToTutor()
                );
    }
}
