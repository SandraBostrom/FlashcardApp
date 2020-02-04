import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class FlashcardPlay {

    private JTextArea display;
    private TextArea answer;
    private ArrayList<Flashcard> cardlist;
    private Iterator cardIterator;
    private Flashcard currentCard;
    private int currentCardIndex;
    private JButton showAnswer;
    private JFrame frame;
    private Boolean isShowAnswer;
    public FlashcardPlay(){

        frame = new JFrame("Flashcard player");
        JPanel mainpanel = new JPanel();
        Font myFont = new Font("Helvetica Neue", Font.BOLD,22);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        display = new JTextArea(10,20);
        display.setFont(myFont);

        JScrollPane qScrollpane = new JScrollPane(display);
        qScrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        showAnswer = new JButton("Show answer");
        mainpanel.add(qScrollpane);
        mainpanel.add(showAnswer);
        showAnswer.addActionListener(new NextCard());


        JMenuBar menuBar = new JMenuBar();
        JMenu filemenu = new JMenu("File");
        JMenuItem loadMenuItem = new JMenuItem("Load card set");
        loadMenuItem.addActionListener(new OpenMenuListener());

        filemenu.add(loadMenuItem);
        menuBar.add(filemenu);

        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, mainpanel);
        frame.setSize(640,500);

        frame.setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FlashcardPlay();
            }
        });
    }
    class NextCard implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(isShowAnswer){
                display.setText(currentCard.getAnswer());
                showAnswer.setText("Next card");
                isShowAnswer = false;
            }
            else{
                if(cardIterator.hasNext()){
                    showNextCard();
                }
                else{
                    display.setText("That was the last card");
                    showAnswer.setEnabled(false);
                }
            }
        }
    }

    private class OpenMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileOpen = new JFileChooser();
            fileOpen.showOpenDialog(frame);
            loadFile(fileOpen.getSelectedFile());
        }
    }
    private void loadFile (File selectedFile){

        cardlist = new ArrayList<Flashcard>();
        try{
            BufferedReader read = new BufferedReader(new FileReader(selectedFile));
            String line = null;
            while ((line = read.readLine())  !=null){
            makeCard(line);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Couldn't read file");
        }

        cardIterator = cardlist.iterator();
        showNextCard();
    }

    private void makeCard(String lineToParse){
        StringTokenizer result = new StringTokenizer(lineToParse,"/");
        if (result.hasMoreTokens()){
            Flashcard card = new Flashcard(result.nextToken(),result.nextToken());
            cardlist.add(card);
            System.out.println("Made a card");

        }
    }
    private void showNextCard(){
        currentCard = (Flashcard) cardIterator.next();
        display.setText(currentCard.getQuestion());
        showAnswer.setText("Show answer");
        isShowAnswer =true;
    }
}
