package com.kyu.webrtc.controller;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebRtcController {

    // 1. SDP Offer 전송 (Peer A -> Peer B)
    @MessageMapping("/offer")
    @SendTo("/topic/offer")
    public String sendOffer(String offer) {
        return offer;
    }

    // 2. SDP Answer 전송 (Peer B -> Peer A)
    @MessageMapping("/answer")
    @SendTo("/topic/answer")
    public String sendAnswer(String answer){
        return answer;
    }

    // 3. ICE Candidate 전송 (Peer A <-> Peer B)
    @MessageMapping("/ice-candidate")
    @SendTo("/topic/ice-candidate")
    public String sendIceCandidate(String candidate) {
        return candidate;
    }

}
