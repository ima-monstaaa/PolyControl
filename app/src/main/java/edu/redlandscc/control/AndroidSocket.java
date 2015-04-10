package edu.redlandscc.control;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.commons.net.telnet.TelnetClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import static edu.redlandscc.control.R.*;
import static edu.redlandscc.control.R.id.*;

public class AndroidSocket extends ActionBarActivity implements View.OnClickListener {

    TextView text;
    EditText edit;
    Button button;
    Button button2;
    Editable server, password, username, command;
    private String SHOWPC = "vcbutton play 2";
    private String SHOWCAM = "vcbutton stop 2";
    private String USER = null;
    private String PASS = "72243888";
    private String CMD = null;
    private String host = null;
    private int port = 24;
    private TelnetClient telnet = new TelnetClient();
    private InputStream in;
    private PrintStream out;
    StringBuffer sb;
    Handler mHandler = new Handler();
    int len;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_socket);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        text = (TextView) findViewById(R.id.text);
        edit = (EditText) findViewById(R.id.edit);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        server = edit.getEditableText();
        String username = "admin";
        String command = null;
        String password = "72243888";
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);
        text.setText("Android Socket" + "\n");

    }

    @Override
            public void onClick(View arg0) {

                switch (v.getId()) {
                    case R.id.button:
                        // do something
                        // TODO Auto-generated method stub
                        text.setText("Android Socket" + "\n");
                        try {
                            telnet.connect(server.toString(), 24);
                            in = telnet.getInputStream();
                            out = new PrintStream(telnet.getOutputStream());
                            telnet.setKeepAlive(true);
                            Thread mThread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    try {
                                        sb = new StringBuffer();
                                        while (true)
                                        {
                                            len = in.read();
                                            String s = Character.toString((char)len);
                                            sb.append( s );
                                            AndroidSocket.this.mHandler.post(new Runnable(){
                                                @Override
                                                public void run() {
                                                    // TODO Auto-generated method stub
                                                    AndroidSocket.this.text.getText();
                                                    AndroidSocket.this.text.append( sb.toString() );
                                                                                }
                                                            });
                                                            System.out.println( sb );
                                                            mylogin();
                                                            mypass();
                                                            mycommand();
                                                        }
                                                    } catch (IOException e) {
                                                        // TODO Auto-generated catch block
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                            mThread.start();
                                        }
                                        catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                }
                    private void mycommand() throws IOException {
                        // TODO Auto-generated method stub
                        if (sb.toString().endsWith("SNMP Enabled:        True")) {
                            out.println(SHOWCAM + "\r\n");
                            out.flush();
                            out.println("exit\r\n");
                            out.flush();
                            try {
                                TimeUnit.SECONDS.sleep(10);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            disconnect();
                        } else
                        if (sb.toString().endsWith("# ")) {
                            out.println(command.toString() + "\r\n");
                            out.flush();
                            out.println("exit\r\n");
                            out.flush();
                            try {
                                TimeUnit.SECONDS.sleep(10);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            disconnect();
                        }
                    }
                    private void mypass() {
                        // TODO Auto-generated method stub
                        String password = "72243888";
                        if (sb.toString().endsWith("Password: ")) {
                            out.println(password + "\r\n");
                            out.flush();
                        } else
                        if (sb.toString().endsWith("password: ")) {
                            out.println(password + "\r\n");
                            out.flush();
                        }
                    }
                    private void mylogin() {
                        // TODO Auto-generated method stub
                        if (sb.toString().endsWith("login: ")) {
                            out.println(username.toString() + "\r");
                            out.flush();
                        }
                    }
                    public void disconnect() {
                        try {
                            in.close();
                            out.close();
                            telnet.disconnect();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }


                    case R.id.button2:
                        // do something
                        // TODO Auto-generated method stub
                        text.setText("Android Socket" + "\n");
                        try {
                            telnet.connect(server.toString(), 24);
                            in = telnet.getInputStream();
                            out = new PrintStream(telnet.getOutputStream());
                            telnet.setKeepAlive(true);
                            Thread mThread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    try {
                                        sb = new StringBuffer();
                                        while (true)
                                        {
                                            len = in.read();
                                            String s = Character.toString((char)len);
                                            sb.append( s );
                                            AndroidSocket.this.mHandler.post(new Runnable(){
                                                @Override
                                                public void run() {
                                                    // TODO Auto-generated method stub
                                                    AndroidSocket.this.text.getText();
                                                    AndroidSocket.this.text.append( sb.toString() );
                                                }
                                            });
                                            System.out.println( sb );
                                            mylogin();
                                            mypass();
                                            mycommand();
                                        }
                                    } catch (IOException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            });
                            mThread.start();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                        private void mycommand() throws IOException {
                            // TODO Auto-generated method stub
                            if (sb.toString().endsWith("SNMP Enabled:        True")) {
                                out.println(SHOWCAM + "\r\n");
                                out.flush();
                                out.println("exit\r\n");
                                out.flush();
                                try {
                                    TimeUnit.SECONDS.sleep(10);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                disconnect();
                            } else
                            if (sb.toString().endsWith("# ")) {
                                out.println(command.toString() + "\r\n");
                                out.flush();
                                out.println("exit\r\n");
                                out.flush();
                                try {
                                    TimeUnit.SECONDS.sleep(10);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                disconnect();
                            }
                        }
                        private void mypass() {
                            // TODO Auto-generated method stub
                            String password = "72243888";
                            if (sb.toString().endsWith("Password: ")) {
                                out.println(password + "\r\n");
                                out.flush();
                            } else
                            if (sb.toString().endsWith("password: ")) {
                                out.println(password + "\r\n");
                                out.flush();
                            }
                        }
                        private void mylogin() {
                            // TODO Auto-generated method stub
                            if (sb.toString().endsWith("login: ")) {
                                out.println(username.toString() + "\r");
                                out.flush();
                            }
                        }
                        public void disconnect() {
                            try {
                                in.close();
                                out.close();
                                telnet.disconnect();
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }



                        break;
                }



/*// TODO Auto-generated method stub
text.setText("Android Socket" + "\n");
                try {
                    telnet.connect(server.toString(), 24);
                    in = telnet.getInputStream();
                    out = new PrintStream(telnet.getOutputStream());
                    telnet.setKeepAlive(true);
                    Thread mThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
        // TODO Auto-generated method stub
                            try {
                                sb = new StringBuffer();
                                while (true)
                                {
                                    len = in.read();
                                    String s = Character.toString((char)len);
                                    sb.append( s );
                                    AndroidSocket.this.mHandler.post(new Runnable(){
                                        @Override
                                        public void run() {
        // TODO Auto-generated method stub
                                            AndroidSocket.this.text.getText();
                                            AndroidSocket.this.text.append( sb.toString() );
                                        }
                                    });
                                    System.out.println( sb );
                                    mylogin();
                                    mypass();
                                    mycommand();
                                }
                            } catch (IOException e) {
        // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });
                    mThread.start();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            private void mycommand() throws IOException {
        // TODO Auto-generated method stub
                if (sb.toString().endsWith("SNMP Enabled:        True")) {
                    out.println(SHOWCAM + "\r\n");
                    out.flush();
                    out.println("exit\r\n");
                    out.flush();
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    disconnect();
                } else
                if (sb.toString().endsWith("# ")) {
                    out.println(command.toString() + "\r\n");
                    out.flush();
                    out.println("exit\r\n");
                    out.flush();
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    disconnect();
                }
            }
            private void mypass() {
        // TODO Auto-generated method stub
                String password = "72243888";
                if (sb.toString().endsWith("Password: ")) {
                    out.println(password + "\r\n");
                    out.flush();
                } else
                if (sb.toString().endsWith("password: ")) {
                    out.println(password + "\r\n");
                    out.flush();
                }
            }
            private void mylogin() {
        // TODO Auto-generated method stub
                if (sb.toString().endsWith("login: ")) {
                    out.println(username.toString() + "\r");
                    out.flush();
                }
            }
            public void disconnect() {
                try {
                    in.close();
                    out.close();
                    telnet.disconnect();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }/*







/*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_android_socket, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Handle action bar item clicks here. The action bar will
        //automatically handle clicks on the Home/Up button, so long
        //as you specify a parent activity in AndroidManifest.xml.

            int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}*/
