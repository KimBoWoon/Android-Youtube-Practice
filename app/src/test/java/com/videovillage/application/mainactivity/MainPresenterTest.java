package com.videovillage.application.mainactivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Null on 2016-12-22.
 */
public class MainPresenterTest {
    @Mock
    MainPresenter mp;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void choiceYoutubeChannel() throws Exception {
        mp.choiceYoutubeChannel("UCTCYh96Ex4lRWMRg0YQvlIQ");
        verify(mp).choiceYoutubeChannel("UCTCYh96Ex4lRWMRg0YQvlIQ");
    }

    @Test
    public void isDebuggable() throws Exception {

    }

    @Test
    public void youtubeChannelInsert() throws Exception {
        doNothing().when(mp).youtubeChannelInsert();
        mp.youtubeChannelInsert();
        verify(mp).youtubeChannelInsert();
    }

    @Test
    public void getYoutubeSubscribeList() throws Exception {
        when(mp.getYoutubeSubscribeList()).thenReturn(new HashMap<String, String>() {
            {
                this.put("망가녀 Mangganyeo", "UCTCYh96Ex4lRWMRg0YQvlIQ");
                this.put("남욱이의 욱기는 일상", "UCIU2ghnE-3MMhLTlh_7hzZQ");
                this.put("HANA 김하나", "UCfTswP_uNy_h86pUjCU410A");
                this.put("귄펭 GUINPEN", "UCkkfPjfgr8LZLrifl3FmNhg");
                this.put("가랏 혜수몬", "UCUlykKBn12YNqb2U2rnogGQ");
                this.put("맹채연구소", "UCEwq4lvvcNHdVWmpW9Je2SQ");
                this.put("안재억", "UC5xBoynZ_GvifP3J0Le12yw");
                this.put("0zoo 영주", "UC7buwq_navFjilxdYaGO0xw");
                this.put("진이 유튜브", "UCIHKbd4QntJvCXQqvRrBp6Q");
                this.put("채르니 Chaerny", "UCZVpc4jDiGdfmJigYivj6pw");
                this.put("공대생 변승주", "UChE5nZAIhWS5vYTRjsUgRpQ");
                this.put("여정을 떠난 여정", "UCsU2RlGgybcLzfmBqnTx-Rw");
                this.put("공대언니 Engin2ring_girl", "UC5R_lLymIFhoFQlqLgZsNqw");
                this.put("예쁘린", "UCGViywDjm32R0UuyaBVE7pQ");
                this.put("신별 ShinByul", "UCUcIUXAGcXpnu8gH1HKL6Tw");
                this.put("이루리 ILULIY", "UCG0JecZ4QvGGfO-asHGhCIA");
                this.put("욱망나생", "UCd2D8vMvf3MPt6T3sncSfeA");
                this.put("이채은 CHAEEUN", "UCk4ZCDRb2lEvh5by4ceGDDw");
                this.put("재인 아카데미 (Jaein Academy)", "UCt-O6YfZqyNkToaS7JqWxkQ");
                this.put("큐피디", "UCV1whfczLoVM5HBgzmuknkg");
                this.put("엘피디", "UCmncNwwVVXvTH4oYIxODxMQ");
                this.put("김피디", "UCPganpI_QsoOiA6-J0C-X-Q");
                this.put("범피디", "UCOpWGjq4Yp7izNRfShkekjw");
                this.put("정아TV", "UCXoyOGFkB1ssgOf7ZjCqQlQ");
                this.put("JK ENT", "UCIB_oNqi62rKnPFb3Toaozw");
                this.put("주", "UCG_1467YPgA4L_EywJKisXw");
                this.put("BEAN", "UCDgz8c2bv-VaTbraQQFdpjQ");
                this.put("유소 (YUSO)", "UCGRxkFCoi0RsWelthcaTjMw");
                this.put("Hemtube (햄튜브)", "UCPydsWBQpnXGICE-XWGNdow");
                this.put("비디오빌리지", "UCENi3E1IyV0nvIIrha8GIvQ");
                this.put("걸스빌리지", "UCedoBeDMzwnPJazRvvoOXhQ");
                this.put("보이즈빌리지", "UCuSaFvVbK9QpZlbn8Vf34RA");
            }
        });
        mp.getYoutubeSubscribeList();
        verify(mp).getYoutubeSubscribeList();
    }
}