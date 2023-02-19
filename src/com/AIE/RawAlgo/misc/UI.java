import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class UI extends JFrame {
    private JPanel drawPanel;
    private JButton penButton;
    private JButton brushButton;
    private JButton eraserButton;
    private JButton rectangleButton;
    private JButton circleButton;
    private JButton lineButton;
    private JButton textButton;
    private JButton colorButton;
    private JComboBox<String> fontComboBox;
    private JTextField textField;
    private JCheckBox boldCheckBox;
    private JCheckBox italicCheckBox;
    private JSlider brushSizeSlider;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem newMenuItem;
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem saveAsMenuItem;
    private JMenuItem importMenuItem;
    private JMenuItem exportMenuItem;
    private JMenu editMenu;
    private JMenuItem undoMenuItem;
    private JMenuItem redoMenuItem;
    private JMenu viewMenu;
    private JMenuItem zoomInMenuItem;
    private JMenuItem zoomOutMenuItem;
    private JMenuItem actualSizeMenuItem;
    private JMenu helpMenu;
    private JMenuItem aboutMenuItem;

    public UI() {
        // Set up the draw panel
        drawPanel = new JPanel();
        drawPanel.setBackground(Color.WHITE);

        // Set up the toolbar
        penButton = new JButton("Pen");
        brushButton = new JButton("Brush");
        eraserButton = new JButton("Eraser");
        rectangleButton = new JButton("Rectangle");
        circleButton = new JButton("Circle");
        lineButton = new JButton("Line");
        textButton = new JButton("Text");
        colorButton = new JButton("Color");
        fontComboBox = new JComboBox<>(new String[] {"Arial", "Calibri", "Comic Sans MS", "Courier New", "Georgia", "Tahoma", "Times New Roman", "Verdana"});
        textField = new JTextField();
        boldCheckBox = new JCheckBox("Bold");
        italicCheckBox = new JCheckBox("Italic");
        brushSizeSlider = new JSlider(0, 100);
        // Set up the menu bar
        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        newMenuItem = new JMenuItem("New");
        openMenuItem = new JMenuItem("Open");
        saveMenuItem = new JMenuItem("Save");
        saveAsMenuItem = new JMenuItem("Save As...");
        importMenuItem = new JMenuItem("Import");
        exportMenuItem = new JMenuItem("Export");
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.add(importMenuItem);
        fileMenu.add(exportMenuItem);
        menuBar.add(fileMenu);

        editMenu = new JMenu("Edit");
        undoMenuItem = new JMenuItem("Undo");
        redoMenuItem = new JMenuItem("Redo");
        editMenu.add(undoMenuItem);
        editMenu.add(redoMenuItem);
        menuBar.add(editMenu);

        viewMenu = new JMenu("View");
        zoomInMenuItem = new JMenuItem("Zoom In");
        zoomOutMenuItem = new JMenuItem("Zoom Out");
        actualSizeMenuItem = new JMenuItem("Actual Size");
        viewMenu.add(zoomInMenuItem);
        viewMenu.add(zoomOutMenuItem);
        viewMenu.add(actualSizeMenuItem);
        menuBar.add(viewMenu);

        helpMenu = new JMenu("Help");
        aboutMenuItem = new JMenuItem("About");
        helpMenu.add(aboutMenuItem);
        menuBar.add(helpMenu);

        // Set up the frame
        setTitle("Paint Tool");
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the frame
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Set up the layout
        setJMenuBar(menuBar);
        setLayout(new BorderLayout());
        add(drawPanel, BorderLayout.CENTER);

        JPanel toolbarPanel = new JPanel();
        toolbarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolbarPanel.add(penButton);
        toolbarPanel.add(brushButton);
        toolbarPanel.add(eraserButton);
        toolbarPanel.add(rectangleButton);
        toolbarPanel.add(circleButton);
        toolbarPanel.add(lineButton);
        toolbarPanel.add(textButton);
        toolbarPanel.add(colorButton);
        toolbarPanel.add(fontComboBox);
        toolbarPanel.add(textField);
        toolbarPanel.add(boldCheckBox);
        toolbarPanel.add(italicCheckBox);
        toolbarPanel.add(brushSizeSlider);
        add(toolbarPanel, BorderLayout.NORTH);

        // Set up the event listeners
        penButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Set the draw mode to pen
            }
        });

        brushButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Set the draw mode to brush
            }
        });

        eraserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Set the draw mode to eraser
            }
        });

        rectangleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Set the draw mode to rectangle
            }
        });
        circleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Set the draw mode to circle
            }
        });

        lineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Set the draw mode to line
            }
        });

        textButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Set the draw mode to text
            }
        });

        colorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open a color picker dialog
            }
        });

        fontComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Set the font for text mode
            }
        });

        boldCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Set the font weight for text mode
            }
        });

        italicCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Set the font style for text mode
            }
        });
        brushSizeSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                // Set the brush size for brush and eraser modes
            }
        });
        newMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear the draw panel
            }
        });

        openMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open a file dialog to load a drawing
            }
        });

        saveMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Save the current drawing to a file
            }
        });

        saveAsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open a file dialog to save the current drawing to a new file
            }
        });

        importMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open a file dialog to import an image
            }
        });

        exportMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open a file dialog to export the current drawing as an image
            }
        });

        undoMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Undo the last action
            }
        });

        redoMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Redo the last undone action
            }
        });

        zoomInMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Zoom in on the drawing
            }
        });

        zoomOutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Zoom out on the drawing
            }
        });
        actualSizeMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                // Set the zoom level to 100%
            }
        });
        aboutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the about dialog
            }
        });

        // Show the frame
        setVisible(true);
    }

    public static void main(String[] args) {
        new UI();
    }
}