package cn.com.sims.action.briefing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class HtmlToPdfInterceptor extends Thread {

    //private static final Logger LOG = LoggerFactory
      //      .getLogger(HtmlToPdfInterceptor.class);

    private InputStream is;

    public HtmlToPdfInterceptor(InputStream is) {
        this.is = is;
    }

    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            br.readLine();
        } catch (IOException e) {
            //LOG.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
