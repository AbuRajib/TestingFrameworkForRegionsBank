package HomePage;

import org.testng.annotations.Test;
import BaseApi.Base;

import java.util.List;

/**
 * Created by HP on 8/15/2015.
 */
public class MainMenu extends Base{

    @Test

    public void menuForHomePage() throws InterruptedException {
            List<String> lists=getListOfTextByXpath(".//*[@id='aspnetForm']/div[3]/div/div[6]/div[2]/div");
            displayText(lists);

            for(int i=1;i<lists.size()+1;i+=2){
                clickByXpath(".//*[@id='aspnetForm']/div[3]/div/div[6]/div[2]/div["+i+"]/a");
                sleepFor(2);
            }
    }

}
