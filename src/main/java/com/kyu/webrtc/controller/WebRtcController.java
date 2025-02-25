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

       /*
     SDP(프로토콜) 와 ice-candidate(데이터개념)는 왜 따로 전송되는가?
     SDP offer/Answer 는 WebRTC 초기 연결을 위한 세션 정보
     Ice-Candidate는 네트워크 경로 최적화(IP, NAT 등)
     SDP는 한번 주고 받으면 되지만 , Ice-candidate는 네트워크 상태에 따라 계속 추가적으로 주고 받아야함
     SDP에는 이 미디어를 어떻게 보낼지?에대한 정보를 담고있음
     v-> SDP 버전, o -> 오너(owner)정보(세션ID , 네트워크정보), s -> 세션이름, c-> 네트워크 연결정보(IP, 포트)
     t -> 타이밍정보(시작/종료시간), m -> 미디어타입(오디오,비디오 등), a -> 추가 속성(코덱 정보, RTP 설정 등 )

    SDP 정보는 클라이언트에서 추출 (WebRTC 의 createOffer()같은 메서드로 호출하면 자동으로 생성)
    Websocket 은 단순히 "전달"만 하는 역할

    SDP -> ICE candidate 순서로 진행, SDP를 사용해 연결 처음에 시도, 연결이 어려운 경우 ICE candidate를 주고 받으며 경로를 찾아가는 방식
    SDP 만으로 연결이 될수도 안될수도 있음
    - 동일한네트워크에서는 SDP 만으로 연결가능
    ICE candidate 가 필요한 이유
    - 공유기, 방화벽 , 서로 다른 네트워크 환경
    Ice Candidate가 NAT를 우회하는 과정
    STUN 서버를 이용해서 자신의 공인IP를 알아내어 Peer B에게 전달

    NAT(Network Address Translation)
    사설IP를 공인IP로 변환하는 역할을하는 기술

    ice candidate의 네트워크 경로란 ?
    IP(공인IP), Port(상대방이 내 데이터를 보낼수 있는 포트) , 전송방식(UDP/TCP), Candidate Type(srfix(서버 리플렉시브) -> STUN 서버를 이용해 NAT를 우회)
    로컬IP(NAT 내부주소), 로컬포트

    * */
}
