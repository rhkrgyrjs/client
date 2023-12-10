package windows;

import javax.swing.*;

import Client.Start;
import form.ChatForm;
import form.GameBoardInfoForm;
import form.LoginReplyForm;
import form.LoginRequestForm;
import hash.SHA256;
import image.Blob;
import image.PicResize;
import login.LoginRequest;
import socket.SendObject;
import swing.ShowMessage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GameBoardWindow extends JFrame
{
	// 직렬화 처리 
	private static final long serialVersionUID = 1L;
	
	// 싱글톤 처리 
	private static GameBoardWindow single_instance = null;
	public static GameBoardWindow getInstance()
	{
		if (single_instance == null) single_instance = new GameBoardWindow();
		return single_instance;
	}
	
	boolean onGame = false;
	
	// 배경, 종 이미지 
	ImageIcon bgImg = null;
	ImageIcon bellImg = null;
	
	// 카드 이미지들 
	ImageIcon cardBackImg = null;
	ImageIcon blankCardImg = null;
	ImageIcon banana1Card = null;
	ImageIcon banana2Card = null;
	ImageIcon banana3Card = null;
	ImageIcon banana4Card = null;
	ImageIcon banana5Card = null;
	ImageIcon lime1Card = null;
	ImageIcon lime2Card = null;
	ImageIcon lime3Card = null;
	ImageIcon lime4Card = null;
	ImageIcon lime5Card = null;
	ImageIcon strawberry1Card = null;
	ImageIcon strawberry2Card = null;
	ImageIcon strawberry3Card = null;
	ImageIcon strawberry4Card = null;
	ImageIcon strawberry5Card = null;
	ImageIcon plum1Card = null;
	ImageIcon plum2Card = null;
	ImageIcon plum3Card = null;
	ImageIcon plum4Card = null;
	ImageIcon plum5Card = null;
	
	// 배경 패널 
	JPanel bgPanel = null;
	
	// 이미지 띄울 라벨 
	JLabel bellImgLabel = null;
	
	JLabel labelA = null;
	JLabel labelB = null;
	JLabel labelC = null;
	JLabel labelD = null;
	
	JLabel myDeck = null;
	JLabel opDeck = null;
	JLabel myDeckCount = null;
	JLabel opDeckCount = null;
	
	// 나/상대의 정보 띄울 라벨 
	ImageIcon opPic = null;
	JLabel myPicLabel = null;
	JLabel myNickNameLabel = null;
	JLabel myRatingLabel = null;
	JLabel myLogLabel = null;
	
	ImageIcon myPic = null;
	JLabel opPicLabel = null;
	JLabel opNickNameLabel = null;
	JLabel opRatingLabel = null;
	JLabel opLogLabel = null;
	
	// 게임 로그 창 
	JTextArea gameLog = null;
	JScrollPane logPane = null;
	
	// 다른 창들 제어
	ChatWindow chatWindow = null;
	LobbyWindow lobbyWindow = null;
	
	public void setChatWindow(ChatWindow cw) {this.chatWindow = cw;}
	public void setLobbyWindow(LobbyWindow lbw) {this.lobbyWindow = lbw;}
	
	public void clear()
	{
		this.onGame = false;
		gameLog.setText("");
        gameLog.append("\n");
        gameLog.append("\n");
        gameLog.append("\n");
        gameLog.append("\n");
        gameLog.append("\n");
        gameLog.append("\n");
        gameLog.append("\n");
        gameLog.append("\n");
        gameLog.append("\n");
        gameLog.append("상대를 기다리는 중입니다...");

        myPic = new ImageIcon("img/op_profile.png");
        opPic = new ImageIcon("img/op_profile.png");
        myPicLabel.setIcon(myPic);
        opPicLabel.setIcon(opPic);
        myNickNameLabel.setText("?");
        opNickNameLabel.setText("?");
        myRatingLabel.setText("elo Rating : ?");
        opRatingLabel.setText("elo Rating : ?");
        myLogLabel.setText("? 승 ? 무 ? 패");
        opLogLabel.setText("? 승 ? 무 ? 패");
        
        myDeckCount.setText("00");
        opDeckCount.setText("00");
        
        labelA.setIcon(blankCardImg);
        labelB.setIcon(blankCardImg);
        labelC.setIcon(blankCardImg);
        labelD.setIcon(blankCardImg);
	}
	
	private GameBoardWindow()
	{
		// 창 기본 세팅 
		setTitle("할리갈리");
		setResizable(false);
		setSize(1200, 800);
		setLayout(null);
		setLocationRelativeTo(null);
		
		// 배경 이미지 로드/패널 처리 
		bgImg = new ImageIcon("img/background.png");
		bgPanel = new JPanel() 
		{
            @Override
            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                bgImg.paintIcon(this, g, 0, 0);
            }
        };
        bgPanel.setLayout(null);
        
      
        // 종 이미지 로드/띄우기 
        bellImg = new ImageIcon("img/bell.png");
        bellImgLabel = new JLabel(bellImg);
        
        // 카드 이미지 로드 
        cardBackImg = new ImageIcon("img/cardback.png");
        blankCardImg = new ImageIcon("img/blank.png");
        banana1Card = new ImageIcon("img/banana1.png");
        banana2Card = new ImageIcon("img/banana2.png");
        banana3Card = new ImageIcon("img/banana3.png");
        banana4Card = new ImageIcon("img/banana4.png");
        banana5Card = new ImageIcon("img/banana5.png");
        lime1Card = new ImageIcon("img/lime1.png");
        lime2Card = new ImageIcon("img/lime2.png");
        lime3Card = new ImageIcon("img/lime3.png");
        lime4Card = new ImageIcon("img/lime4.png");
        lime5Card = new ImageIcon("img/lime5.png");
        strawberry1Card = new ImageIcon("img/strawberry1.png");
        strawberry2Card = new ImageIcon("img/strawberry2.png");
        strawberry3Card = new ImageIcon("img/strawberry3.png");
        strawberry4Card = new ImageIcon("img/strawberry4.png");
        strawberry5Card = new ImageIcon("img/strawberry5.png");
        plum1Card = new ImageIcon("img/plum1.png");
        plum2Card = new ImageIcon("img/plum2.png");
        plum3Card = new ImageIcon("img/plum3.png");
        plum4Card = new ImageIcon("img/plum4.png");
        plum5Card = new ImageIcon("img/plum5.png");
        
        // 정보 창들 생성
        myPic = new ImageIcon("img/op_profile.png");
        opPic = new ImageIcon("img/op_profile.png");
        myPicLabel = new JLabel(myPic);
        opPicLabel = new JLabel(opPic);
        myNickNameLabel = new JLabel("?");
        opNickNameLabel = new JLabel("?");
        myRatingLabel = new JLabel("elo Rating : ?");
        opRatingLabel = new JLabel("elo Rating : ?");
        myLogLabel = new JLabel("? 승 ? 무 ? 패");
        opLogLabel = new JLabel("? 승 ? 무 ? 패");
        // 정보 창들 바운딩
        opPicLabel.setBounds(10, 10, 150, 150);
        opNickNameLabel.setBounds(10, 165, 250, 20);
        opLogLabel.setBounds(10, 180, 250, 20);
        opRatingLabel.setBounds(10, 195, 250, 20);
        
        myPicLabel.setBounds(10, 540, 150, 150);
        myNickNameLabel.setBounds(10, 690, 250, 20);
        myLogLabel.setBounds(10, 705, 250, 20);
        myRatingLabel.setBounds(10, 720, 250, 20);
        
        // 로그 창 관련 세팅
        gameLog = new JTextArea();
        gameLog.append("\n");
        gameLog.append("\n");
        gameLog.append("\n");
        gameLog.append("\n");
        gameLog.append("\n");
        gameLog.append("\n");
        gameLog.append("\n");
        gameLog.append("\n");
        gameLog.append("\n");
        gameLog.append("상대를 기다리는 중입니다...");
        logPane = new JScrollPane(gameLog);
        logPane.setBounds(10, 220, 200, 310);
        gameLog.setEditable(false);
        gameLog.setFocusable(false);
        bgPanel.add(logPane);
        
        
        bgPanel.add(opPicLabel);
        bgPanel.add(opNickNameLabel);  
        bgPanel.add(opLogLabel);      
        bgPanel.add(opRatingLabel);
        bgPanel.add(myPicLabel);
        bgPanel.add(myNickNameLabel);
        bgPanel.add(myLogLabel);
        bgPanel.add(myRatingLabel);
        
        // 카드 뒷면 띄우기 
        myDeck = new JLabel(cardBackImg);
        opDeck = new JLabel(cardBackImg);
        
        // A, B, C, D 기본값 띄우기 
        labelA = new JLabel(blankCardImg);
        labelB = new JLabel(blankCardImg);
        labelC = new JLabel(blankCardImg);
        labelD = new JLabel(blankCardImg);
        
        // 나/상대의 덱 장수 표시하는 라벨 띄우기 
        myDeckCount = new JLabel("00");
        opDeckCount = new JLabel("00");
        
        // 컴포넌트 파운딩 
        bgPanel.setBounds(0, 0, 1200, 800);
        bellImgLabel.setBounds(510, 310, 180, 160);
        myDeck.setBounds(551, 620, 90, 120);
        opDeck.setBounds(551, 20, 90, 120);
        labelA.setBounds(405, 200, 90, 120);
        labelB.setBounds(700, 200, 90, 120);
        labelC.setBounds(405, 450, 90, 120);
        labelD.setBounds(700, 450, 90, 120);
        myDeckCount.setBounds(590, 150, 50, 20);
        opDeckCount.setBounds(590, 590, 50, 20);
        
        // 카드 펼치는 리스너 이벤트 처리 
        addKeyListener(new FlipNRing(this));
        
        
        // 컴포넌트 add 
        bgPanel.add(bellImgLabel);
        
        bgPanel.add(myDeck);
        bgPanel.add(opDeck);
        bgPanel.add(labelA);
        bgPanel.add(labelB);
        bgPanel.add(labelC);
        bgPanel.add(labelD);
        
        bgPanel.add(myDeckCount);
        bgPanel.add(opDeckCount);
        
        add(bgPanel);
		
		// 창 닫았을때 이벤트 리스너 
		this.addWindowListener(new Close(this));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		setVisible(false);
	}
	
	public void setMyInfo()
	{
		LoginRequestForm toSend = new LoginRequestForm();
		toSend.setReqType(10);
		toSend.setId(Start.myId);
		LoginReplyForm received = LoginRequest.toServer_getObj(toSend);
		if (received.getResult())
		{
			// 정보 불러오기 성공 
			BufferedImage profile = PicResize.getIngameProfile(Blob.toBufferedImage(received.getPicBlob()));
			ImageIcon pic = new ImageIcon(profile);
			this.myPicLabel.setIcon(pic);
			this.myNickNameLabel.setText(received.getId() + " @ " + received.getNickName());
			this.myLogLabel.setText(received.getSearchResult()[0][0]);
			this.myRatingLabel.setText(received.getSearchResult()[0][1]);
		}
		else
		{
			// 정보 불러오기 실패 
			this.myPicLabel.setText("정보를 불러올 수 없습니다.");
		}
	}
	
	public void setOpInfo(String id)
	{
		LoginRequestForm toSend = new LoginRequestForm();
		toSend.setReqType(10);
		toSend.setId(id);
		LoginReplyForm received = LoginRequest.toServer_getObj(toSend);
		if (received.getResult())
		{
			// 정보 불러오기 성공 
			BufferedImage profile = PicResize.getIngameProfile(Blob.toBufferedImage(received.getPicBlob()));
			ImageIcon pic = new ImageIcon(profile);
			this.opPicLabel.setIcon(pic);
			this.opNickNameLabel.setText(received.getId() + " @ " + received.getNickName());
			this.opLogLabel.setText(received.getSearchResult()[0][0]);
			this.opRatingLabel.setText(received.getSearchResult()[0][1]);
		}
		else
		{
			// 정보 불러오기 실패 
			this.opPicLabel.setText("정보를 불러올 수 없습니다.");
		}
		this.onGame = true;
	}
	
	public void updateWindow(ChatForm data)
	{
		this.gameLog.append(data.getMsg());
		this.gameLog.append("\n");
		try {this.gameLog.setCaretPosition(this.gameLog.getDocument().getLength());}
		catch (IllegalArgumentException e) {}
		
		GameBoardInfoForm boardInfo = data.getBoardInfo();
		// 서버에서 게임보드 정보 가져와 업데이트함.
		if (Start.myId.equals(data.getRoomId()))
		{
			// 내가 host라면 
			this.opDeckCount.setText(Integer.toString(boardInfo.getHostDeckCount()));
			this.myDeckCount.setText(Integer.toString(boardInfo.getGuestDeckCount()));
		}
		else
		{
			// 내가 guest라면 
			this.myDeckCount.setText(Integer.toString(boardInfo.getHostDeckCount()));
			this.opDeckCount.setText(Integer.toString(boardInfo.getGuestDeckCount()));
		}
		
		// fruit : 0~4 사이의 값, 0-빈카드 1-바나나, 2-라임, 3-딸기, 4-자두 
		// number : 0~5 사이의 값 (0은 빈카드) 
		// A 카드 업데이트.
		switch(boardInfo.getAFront().getNumber())
		{
			case 0:
				this.labelA.setIcon(blankCardImg);
			break;
		
			case 1:
				switch(boardInfo.getAFront().getFruit())
				{
					case 1:
						this.labelA.setIcon(banana1Card);
					break;
					
					case 2:
						this.labelA.setIcon(lime1Card);
					break;
					
					case 3:
						this.labelA.setIcon(strawberry1Card);
					break;
					
					case 4:
						this.labelA.setIcon(plum1Card);
					break;
				}
			break;
			
			case 2:
				switch(boardInfo.getAFront().getFruit())
				{	
					case 1:
						this.labelA.setIcon(banana2Card);
					break;
					
					case 2:
						this.labelA.setIcon(lime2Card);
					break;
					
					case 3:
						this.labelA.setIcon(strawberry2Card);
					break;
					
					case 4:
						this.labelA.setIcon(plum2Card);
					break;
				}
			break;
			
			case 3:
				switch(boardInfo.getAFront().getFruit())
				{
					case 1:
						this.labelA.setIcon(banana3Card);
					break;
					
					case 2:
						this.labelA.setIcon(lime3Card);
					break;
					
					case 3:
						this.labelA.setIcon(strawberry3Card);
					break;
					
					case 4:
						this.labelA.setIcon(plum3Card);
					break;
				}
			break;
			
			case 4:
				switch(boardInfo.getAFront().getFruit())
				{
					case 1:
						this.labelA.setIcon(banana4Card);
					break;
					
					case 2:
						this.labelA.setIcon(lime4Card);
					break;
					
					case 3:
						this.labelA.setIcon(strawberry4Card);
					break;
					
					case 4:
						this.labelA.setIcon(plum4Card);
					break;
				}
			break;
			
			case 5:
				switch(boardInfo.getAFront().getFruit())
				{
					case 1:
						this.labelA.setIcon(banana5Card);
					break;
					
					case 2:
						this.labelA.setIcon(lime5Card);
					break;
					
					case 3:
						this.labelA.setIcon(strawberry5Card);
					break;
					
					case 4:
						this.labelA.setIcon(plum5Card);
					break;
				}
			break;
		}
		
		// B 카드 업데이트 
		switch(boardInfo.getBFront().getNumber())
		{
			case 0:
				this.labelB.setIcon(blankCardImg);
			break;
		
			case 1:
				switch(boardInfo.getBFront().getFruit())
				{
					case 1:
						this.labelB.setIcon(banana1Card);
					break;
					
					case 2:
						this.labelB.setIcon(lime1Card);
					break;
					
					case 3:
						this.labelB.setIcon(strawberry1Card);
					break;
					
					case 4:
						this.labelB.setIcon(plum1Card);
					break;
				}
			break;
			
			case 2:
				switch(boardInfo.getBFront().getFruit())
				{	
					case 1:
						this.labelB.setIcon(banana2Card);
					break;
					
					case 2:
						this.labelB.setIcon(lime2Card);
					break;
					
					case 3:
						this.labelB.setIcon(strawberry2Card);
					break;
					
					case 4:
						this.labelB.setIcon(plum2Card);
					break;
				}
			break;
			
			case 3:
				switch(boardInfo.getBFront().getFruit())
				{
					case 1:
						this.labelB.setIcon(banana3Card);
					break;
					
					case 2:
						this.labelB.setIcon(lime3Card);
					break;
					
					case 3:
						this.labelB.setIcon(strawberry3Card);
					break;
					
					case 4:
						this.labelB.setIcon(plum3Card);
					break;
				}
			break;
			
			case 4:
				switch(boardInfo.getBFront().getFruit())
				{
					case 1:
						this.labelB.setIcon(banana4Card);
					break;
					
					case 2:
						this.labelB.setIcon(lime4Card);
					break;
					
					case 3:
						this.labelB.setIcon(strawberry4Card);
					break;
					
					case 4:
						this.labelB.setIcon(plum4Card);
					break;
				}
			break;
			
			case 5:
				switch(boardInfo.getBFront().getFruit())
				{
					case 1:
						this.labelB.setIcon(banana5Card);
					break;
					
					case 2:
						this.labelB.setIcon(lime5Card);
					break;
					
					case 3:
						this.labelB.setIcon(strawberry5Card);
					break;
					
					case 4:
						this.labelB.setIcon(plum5Card);
					break;
				}
			break;
		}
		
		// C 카드 업데이트 
		switch(boardInfo.getCFront().getNumber())
		{
			case 0:
				this.labelC.setIcon(blankCardImg);
			break;
				
			case 1:
				switch(boardInfo.getCFront().getFruit())
				{
					case 1:
						this.labelC.setIcon(banana1Card);
					break;
							
					case 2:
						this.labelC.setIcon(lime1Card);
					break;
							
					case 3:
						this.labelC.setIcon(strawberry1Card);
					break;
							
					case 4:
						this.labelC.setIcon(plum1Card);
					break;
				}
			break;
					
					case 2:
						switch(boardInfo.getCFront().getFruit())
						{	
							case 1:
								this.labelC.setIcon(banana2Card);
							break;
							
							case 2:
								this.labelC.setIcon(lime2Card);
							break;
							
							case 3:
								this.labelC.setIcon(strawberry2Card);
							break;
							
							case 4:
								this.labelC.setIcon(plum2Card);
							break;
						}
					break;
					
					case 3:
						switch(boardInfo.getCFront().getFruit())
						{
							case 1:
								this.labelC.setIcon(banana3Card);
							break;
							
							case 2:
								this.labelC.setIcon(lime3Card);
							break;
							
							case 3:
								this.labelC.setIcon(strawberry3Card);
							break;
							
							case 4:
								this.labelC.setIcon(plum3Card);
							break;
						}
					break;
					
					case 4:
						switch(boardInfo.getCFront().getFruit())
						{
							case 1:
								this.labelC.setIcon(banana4Card);
							break;
							
							case 2:
								this.labelC.setIcon(lime4Card);
							break;
							
							case 3:
								this.labelC.setIcon(strawberry4Card);
							break;
							
							case 4:
								this.labelC.setIcon(plum4Card);
							break;
						}
					break;
					
					case 5:
						switch(boardInfo.getCFront().getFruit())
						{
							case 1:
								this.labelC.setIcon(banana5Card);
							break;
							
							case 2:
								this.labelC.setIcon(lime5Card);
							break;
							
							case 3:
								this.labelC.setIcon(strawberry5Card);
							break;
							
							case 4:
								this.labelC.setIcon(plum5Card);
							break;
						}
					break;
				}
		

		// D 카드 업데이트 
		switch(boardInfo.getDFront().getNumber())
		{
			case 0:
				this.labelD.setIcon(blankCardImg);
			break;
				
			case 1:
				switch(boardInfo.getDFront().getFruit())
				{
					case 1:
						this.labelD.setIcon(banana1Card);
					break;
							
					case 2:
						this.labelD.setIcon(lime1Card);
					break;
							
					case 3:
						this.labelD.setIcon(strawberry1Card);
					break;
							
					case 4:
						this.labelD.setIcon(plum1Card);
					break;
				}
			break;
					
					case 2:
						switch(boardInfo.getDFront().getFruit())
						{	
							case 1:
								this.labelD.setIcon(banana2Card);
							break;
							
							case 2:
								this.labelD.setIcon(lime2Card);
							break;
							
							case 3:
								this.labelD.setIcon(strawberry2Card);
							break;
							
							case 4:
								this.labelD.setIcon(plum2Card);
							break;
						}
					break;
					
					case 3:
						switch(boardInfo.getDFront().getFruit())
						{
							case 1:
								this.labelD.setIcon(banana3Card);
							break;
							
							case 2:
								this.labelD.setIcon(lime3Card);
							break;
							
							case 3:
								this.labelD.setIcon(strawberry3Card);
							break;
							
							case 4:
								this.labelD.setIcon(plum3Card);
							break;
						}
					break;
					
					case 4:
						switch(boardInfo.getDFront().getFruit())
						{
							case 1:
								this.labelD.setIcon(banana4Card);
							break;
							
							case 2:
								this.labelD.setIcon(lime4Card);
							break;
							
							case 3:
								this.labelD.setIcon(strawberry4Card);
							break;
							
							case 4:
								this.labelD.setIcon(plum4Card);
							break;
						}
					break;
					
					case 5:
						switch(boardInfo.getDFront().getFruit())
						{
							case 1:
								this.labelD.setIcon(banana5Card);
							break;
							
							case 2:
								this.labelD.setIcon(lime5Card);
							break;
							
							case 3:
								this.labelD.setIcon(strawberry5Card);
							break;
							
							case 4:
								this.labelD.setIcon(plum5Card);
							break;
						}
					break;
				}
		
	}
	
	public void flipCard() 
	{
		// 서버로 방 제목, 아이디, 카드 펼치기 명령 보냄 
		// System.out.println("카드펼침 ");
		ChatForm toSend = new ChatForm(2, Start.roomId, Start.myId, Start.myNickname, "flip");
		SendObject.withSocket(Start.connSocket, toSend);
	}
	
	public void ringBell()
	{
		// 서버로 방 제목, 아이디, 종 울리기 명령 보냄 
		// System.out.println("종 울림 ");
		ChatForm toSend = new ChatForm(2, Start.roomId, Start.myId, Start.myNickname, "ring");
		SendObject.withSocket(Start.connSocket, toSend);
	}
	
	
	class FlipNRing implements KeyListener
	{
		GameBoardWindow gbw = null;
		FlipNRing(GameBoardWindow gbw) {this.gbw = gbw;}
		
		@Override
		public void keyTyped(KeyEvent e) 
		{
		}
		
		@Override
		public void keyPressed(KeyEvent e) 
		{
			if (e.getKeyCode() == KeyEvent.VK_SPACE)
				gbw.ringBell();
			else if (e.getKeyCode() == KeyEvent.VK_UP)
				gbw.flipCard();
		}
		
		@Override
		public void keyReleased(KeyEvent e) 
		{
		}
	}
	
	class Close extends WindowAdapter
	{
		GameBoardWindow gbw = null;
		Close(GameBoardWindow gbw) {this.gbw = gbw;}
		
        @Override
        public void windowClosing(WindowEvent e) 
        {
        	if (onGame == true ) {
        	if (ShowMessage.confirm("게임 종료", "탈주하시겠습니까? 게임은 패배 처리됩니다."))
        	{
        		System.exit(0);
        	}
        	else
        	{
        		gbw.setVisible(true);
        	}}
        	else
        	{
        		gbw.setVisible(false);
        		gbw.clear();
        		// 서버에 방 없애게 요청 보내기 
    			LoginRequestForm toSend = new LoginRequestForm();
    			toSend.setReqType(12);
    			toSend.setRoomName(Start.roomId);
    			Start.roomId = "@ServerMain";
    			LoginRequest.toServer(toSend);
        		gbw.lobbyWindow.setVisible(true);
        		gbw.chatWindow.setVisible(true);
        	}
        }
	}
}
