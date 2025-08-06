import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import components.naturalnumber.NaturalNumber;

/**
 * View class.
 *
 * @author Yonis Mohamud
 */
public final class NNCalcView1 extends JFrame implements NNCalcView {

    /**
     * Controller object registered with this view to observe user-interaction
     * events.
     */

    private static final long serialVersionUID = 1L;

    /**
     * a controller object registered with this view to act as a listener for
     * the view.
     */
    private NNCalcController controller;

    /**
     * State of user interaction: last event "seen".
     */
    private enum State {
        /**
         * Last event was clear, enter, another operator, or digit entry, resp.
         */
        SAW_CLEAR, SAW_ENTER_OR_SWAP, SAW_OTHER_OP, SAW_DIGIT
    }

    /**
     * State variable to keep track of which event happened last; needed to
     * prepare for digit to be added to bottom operand.
     */
    private State currentState;

    /**
     * Text areas.
     */
    private JTextArea tTop, tBottom;

    /**
     * Operator and related buttons.
     */
    private final JButton bClear, bSwap, bEnter, bAdd, bSubtract, bMultiply, bDivide,
            bPower, bRoot;

    /**
     * Digit entry buttons.
     */
    private final JButton[] bDigits;

    /**
     * Useful constants.
     */
    private static final int TEXT_AREA_HEIGHT = 5, TEXT_AREA_WIDTH = 20,
            DIGIT_BUTTONS = 10, MAIN_BUTTON_PANEL_GRID_ROWS = 4,
            MAIN_BUTTON_PANEL_GRID_COLUMNS = 4, SIDE_BUTTON_PANEL_GRID_ROWS = 3,
            SIDE_BUTTON_PANEL_GRID_COLUMNS = 1, CALC_GRID_ROWS = 3, CALC_GRID_COLUMNS = 1,
            TEN = 10, NINE = 9, EIGHT = 8, SEVEN = 7, SIX = 6, FIVE = 5, FOUR = 4,
            THREE = 3, TWO = 2, ONE = 1;

    /**
     * No argument constructor.
     */
    public NNCalcView1() {
        // Create the JFrame being extended

        /*
         * Call the JFrame (superclass) constructor with a String parameter to
         * name the window in its title bar
         */
        super("Natural Number Calculator");

        // Set up the GUI widgets --------------------------------------------

        /*
         * Set up initial state of GUI to behave like last event was "Clear";
         * currentState is not a GUI widget per se, but is needed to process
         * digit button events appropriately
         */

        this.currentState = State.SAW_CLEAR;

        // our basic operator buttons
        this.bAdd = new JButton("+");
        this.bSubtract = new JButton("-");
        this.bMultiply = new JButton("*");
        this.bDivide = new JButton("/");
        this.bPower = new JButton("Power");
        this.bRoot = new JButton("Root");

        // our utility buttons

        this.bEnter = new JButton("Enter");
        this.bClear = new JButton("Clear");
        this.bSwap = new JButton("Swap");

        // loop to create our digitbtns

        this.bDigits = new JButton[TEN];

        for (int i = 0; i < DIGIT_BUTTONS; i++) {
            this.bDigits[i] = new JButton(Integer.toString(i));
        }

        //create the JTextAreas for word wrap and ensure they are uneditable
        this.tTop = new JTextArea("", TEXT_AREA_HEIGHT, TEXT_AREA_WIDTH);
        this.tBottom = new JTextArea("", TEXT_AREA_HEIGHT, TEXT_AREA_WIDTH);

        this.tTop.setEditable(false);
        this.tTop.setLineWrap(true);

        this.tBottom.setEditable(false);
        this.tBottom.setLineWrap(true);

        //disable specific buttons at the start
        this.bDivide.setEnabled(true);
        this.bRoot.setEnabled(true);

        //create jscroll for our text fields

        JScrollPane tScrollP = new JScrollPane(this.tTop);
        JScrollPane bScrollP = new JScrollPane(this.tBottom);

        //create panel of buttons with grid style layout that will hold all our buttons
        JPanel btnPanel = new JPanel(new GridLayout(MAIN_BUTTON_PANEL_GRID_ROWS,
                MAIN_BUTTON_PANEL_GRID_COLUMNS));

        // add both digit buttons and operator buttons
        //to the main part of our button panel

        btnPanel.add(this.bDigits[SEVEN]);
        btnPanel.add(this.bDigits[EIGHT]);
        btnPanel.add(this.bDigits[NINE]);
        btnPanel.add(this.bAdd);
        btnPanel.add(this.bDigits[FOUR]);
        btnPanel.add(this.bDigits[FIVE]);
        btnPanel.add(this.bDigits[SIX]);
        btnPanel.add(this.bSubtract);
        btnPanel.add(this.bDigits[ONE]);
        btnPanel.add(this.bDigits[TWO]);
        btnPanel.add(this.bDigits[THREE]);
        btnPanel.add(this.bMultiply);
        btnPanel.add(this.bDigits[0]);
        btnPanel.add(this.bPower);
        btnPanel.add(this.bRoot);
        btnPanel.add(this.bDivide);

        // side button panel with gridlayout as well
        JPanel sideBtnPanel = new JPanel(new GridLayout(SIDE_BUTTON_PANEL_GRID_ROWS,
                SIDE_BUTTON_PANEL_GRID_COLUMNS));

        // add out basic utility buttons as well
        sideBtnPanel.add(this.bClear);
        sideBtnPanel.add(this.bSwap);
        sideBtnPanel.add(this.bEnter);

        // create composite layout of buttons with design layout of grid flow
        JPanel compBtnPanel = new JPanel(new FlowLayout());

        //add both side & main btn panels to composite panel of btns
        compBtnPanel.add(btnPanel);
        compBtnPanel.add(sideBtnPanel);

        this.setLayout(new GridLayout(CALC_GRID_ROWS, CALC_GRID_COLUMNS));

        // Set layout of main frame to a gridstyle design
        this.add(tScrollP);
        this.add(bScrollP);
        this.add(compBtnPanel);

        // action listeners for our utility buttons get clicked some action is preformed
        this.bAdd.addActionListener(this);
        this.bSubtract.addActionListener(this);
        this.bMultiply.addActionListener(this);
        this.bDivide.addActionListener(this);
        this.bRoot.addActionListener(this);
        this.bPower.addActionListener(this);
        this.bEnter.addActionListener(this);
        this.bClear.addActionListener(this);
        this.bSwap.addActionListener(this);

        for (int i = 0; i < DIGIT_BUTTONS; i++) {
            this.bDigits[i].addActionListener(this);
        }

        // Pack the frame, set default close operation, and make it visible
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    //we are registering our observer which is the controller to our view
    @Override
    public void registerObserver(NNCalcController controller) {

        this.controller = controller;

    }
    //the display will be updated with the top natural number

    @Override
    public void updateTopDisplay(NaturalNumber n) {

        this.tTop.setText(n.toString());

    }

    //the display will be updated with the bottom natural number
    @Override
    public void updateBottomDisplay(NaturalNumber n) {

        this.tBottom.setText(n.toString());

    }

    //update the enabled state of our subtract btn
    @Override
    public void updateSubtractAllowed(boolean allowed) {

        this.bSubtract.setEnabled(allowed);

    }

    //update the enabled state of our divide btn
    @Override
    public void updateDivideAllowed(boolean allowed) {
        this.bDivide.setEnabled(allowed);

    }

    //update the enabled state of our power btn

    @Override
    public void updatePowerAllowed(boolean allowed) {
        this.bPower.setEnabled(allowed);

    }

    //update the enabled state of our root btn

    @Override
    public void updateRootAllowed(boolean allowed) {
        this.bRoot.setEnabled(allowed);

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        /*
         * Set cursor to indicate computation on-going; this matters only if
         * processing the event might take a noticeable amount of time as seen
         * by the user
         */
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        /*
         * Determine which event has occurred that we are being notified of by
         * this callback; in this case, the source of the event (i.e, the widget
         * calling actionPerformed) is all we need because only buttons are
         * involved here, so the event must be a button press; in each case,
         * tell the controller to do whatever is needed to update the model and
         * to refresh the view
         */
        Object source = event.getSource();
        if (source == this.bClear) {
            this.controller.processClearEvent();
            this.currentState = State.SAW_CLEAR;
        } else if (source == this.bSwap) {
            this.controller.processSwapEvent();
            this.currentState = State.SAW_ENTER_OR_SWAP;
        } else if (source == this.bEnter) {
            this.controller.processEnterEvent();
            this.currentState = State.SAW_ENTER_OR_SWAP;
        } else if (source == this.bAdd) {
            this.controller.processAddEvent();
            this.currentState = State.SAW_OTHER_OP;
        } else if (source == this.bSubtract) {
            this.controller.processSubtractEvent();
            this.currentState = State.SAW_OTHER_OP;
        } else if (source == this.bMultiply) {
            this.controller.processMultiplyEvent();
            this.currentState = State.SAW_OTHER_OP;
        } else if (source == this.bDivide) {
            this.controller.processDivideEvent();
            this.currentState = State.SAW_OTHER_OP;
        } else if (source == this.bPower) {
            this.controller.processPowerEvent();
            this.currentState = State.SAW_OTHER_OP;
        } else if (source == this.bRoot) {
            this.controller.processRootEvent();
            this.currentState = State.SAW_OTHER_OP;
        } else {
            for (int i = 0; i < DIGIT_BUTTONS; i++) {
                if (source == this.bDigits[i]) {
                    switch (this.currentState) {
                        case SAW_ENTER_OR_SWAP:
                            this.controller.processClearEvent();
                            break;
                        case SAW_OTHER_OP:
                            this.controller.processEnterEvent();
                            this.controller.processClearEvent();
                            break;
                        default:
                            break;
                    }
                    this.controller.processAddNewDigitEvent(i);
                    this.currentState = State.SAW_DIGIT;
                    break;
                }
            }
        }
        /*
         * Set the cursor back to normal (because we changed it at the beginning
         * of the method body)
         */
        this.setCursor(Cursor.getDefaultCursor());
    }

}
