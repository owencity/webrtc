# WebRtc 구현

WebRTC signaling 위한 개인 프로젝트

## 중요내용
- WebRTC는 원래 서버없이 P2P(브라우저끼리 직접연결) 통신을 할 수 있는 기술
- 서버없이도 가능한 경우
    - 같은 Wi-Fi 또는 로컬 네트워크
    - 동일한 공유기 
    - 내부망
- 현실에서는 대부분 NAT(Network Address Translation) 또는 방화벽 뒤에 있음
- 공인 IP없이 외부에서 직접 접속 불가 
그래서 P2P 연결위해 SDP(Session Description Protocol), 
ICE candidate(Interactive Connectivity Establishment) 정보를 주고받게함
- SDP는 미디어 스트림 정보 교환, ICE candidate는 IP와PORT 정보 교환 
- 브라우저는 서로의 IP주소를 직접 알수 없다 , 위의 정보를 주고받기 위한 "중간 역할" 해줄 서버가 필요 
이 역할을 하는것이 Signaling 서버(STOMP / Websocket / REST API)
WebRTC 에는 Signaling 기능이 없어서 따로 서버측에서 구현
## 왜 REST API 가아닌 Websocket , STOMP 인가?
 
# Rest API signaling 방식 문제점
- 클라이언트가 수동으로 SDP/ICE candidate 서버로 전송해야함
- ICE Candidate 교환이 비효율적 
- 서버가 모든 요청을 처리 
- 실시간성이 떨어지고 서버 부담이 커짐

# STOMP/WebSocket 사용하는 이유 
- Websocket은 실시간 연결을 유지하므로 Polling 이 필요없음
  (Rest API는 Peer B가 주기적으로 "새로운 메시지 있는지" 확인)
- ICE Candidate 교환이 빠름
    - 여러개의 ICE Candidate를 계속 전송할수있는데 즉시 상대방에게 전송 가능 , 각각의 HTTP 요청을 보낼 필요가 없어짐
- STOMP 사용시 구독방식으로 관리 가능
- 1:N 다중 연결도 쉽게 확장 가능

## STUN 사용 이유?
- STUN(Session Traversal Utilities for NAT) 서버는 클라이언트의 "공인 IP & 포트"를 알려주는 역할을 함
- "P2P 연결을 돕는 역할"만 함, Peer-to-Peer 이므로 그룹 영상통화는 불가 
- NAT 내부에서는 클라이언트가 자신의 공인 IP를 직접 알수 없음
- STUN 서버로부터 받은 공인IP & PORT를 ICE candidate로 생성
- 대기업 네트워크 나 일부 ISP에서는 STUN이 동작하지않을수 있음 이때 TURN 서버가 필요
- 예 : 1:1 영상 통화 (P2P 연결)
## SFU(Selective Forwarding Unit)? 
- SFU서버(Janus 등 여러가지 있음)는 "미디어 데이터를 중계 하는 서버"
- 각 Peer는 SFU에 미디어 스트림을 보내고, SFU가 이 스트림을 여러 Peer에게 전달 
- P2P 연결이 아니라 중앙 서버를 통한 다중 접속(1:N , N:N)이 가능 
- 예 : 그룹영상 통화, 라이브 스트리밍, Zoom