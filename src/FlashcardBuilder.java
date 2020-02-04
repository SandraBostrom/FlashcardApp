import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class FlashcardBuilder {


    private JTextArea question;
    private JTextArea answer;
    private ArrayList<Flashcard> cardlist;
    private JFrame frame;

    public FlashcardBuilder() {

        frame = new JFrame("Flashcard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        Font greatFont = new Font("Helvetica Neue", Font.BOLD, 21);
        question = new JTextArea(6, 20);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(greatFont);

        JScrollPane qScrollpane = new JScrollPane(question);
        qScrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        answer = new JTextArea(6, 20);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(greatFont);

        JScrollPane aJScrollPane = new JScrollPane(answer);
        aJScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        aJScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JButton nextButton = new JButton("Next card");
        JLabel questionLabel = new JLabel("Question");
        JLabel answerLabel = new JLabel("Answer");

        cardlist = new ArrayList<Flashcard>();

        mainPanel.add(questionLabel);
        mainPanel.add(qScrollpane);
        mainPanel.add(answerLabel);
        mainPanel.add(aJScrollPane);
        mainPanel.add(nextButton);
        nextButton.addActionListener(new NextCardListener());


        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newfile = new JMenuItem("New");
        JMenuItem savefile = new JMenuItem("Save");

        fileMenu.add(newfile);
        fileMenu.add(savefile);
        menuBar.add(fileMenu);

        newfile.addActionListener(new NewMenuItemListener());
        savefile.addActionListener(new SaveMenuItemListener());
        frame.setJMenuBar(menuBar);

        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(500, 600);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FlashcardBuilder();
            }
        });

    }


    class NextCardListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Flashcard card = new Flashcard(question.getText(), answer.getText());
            cardlist.add(card);
            System.out.println("Size of arraylist " + cardlist.size());
            ClearCard();

        }
    }


    class NewMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("new file clicked");
        }
    }

    class SaveMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
        Flashcard card = new Flashcard(question.getText(),answer.getText());
        cardlist.add(card);

        JFileChooser fileSave = new JFileChooser();
        fileSave.showSaveDialog(frame);
        saveFile(fileSave.getSelectedFile());
        }

    }

    private void ClearCard() {
        question.setText("");
        answer.setText("");
        question.requestFocus();
    }
    private void saveFile (File selectedFile){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
            Iterator<Flashcard> cardIterator = cardlist.iterator();
            while (cardIterator.hasNext()){
                Flashcard card = (Flashcard)cardIterator.next();
                writer.write(card.getQuestion() + "/");
                writer.write(card.getAnswer()+"\n");
            }
        }
        catch (Exception e){
            System.out.println("Coudn't open file");
            e.printStackTrace();
        }

    }
}
