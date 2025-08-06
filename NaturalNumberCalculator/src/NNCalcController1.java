import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Controller class.
 *
 * @author Yonis Mohamud
 */
public final class NNCalcController1 implements NNCalcController {

    /**
     * Model object.
     */
    private final NNCalcModel model;

    /**
     * View object.
     */
    private final NNCalcView view;

    /**
     * Useful constants.
     */
    private static final NaturalNumber TWO = new NaturalNumber2(2),
            INT_LIMIT = new NaturalNumber2(Integer.MAX_VALUE);

    /**
     * Updates this.view to display this.model, and to allow only operations
     * that are legal given this.model.
     *
     * @param model
     *            the model
     * @param view
     *            the view
     * @ensures [view has been updated to be consistent with model]
     */
    private static void updateViewToMatchModel(NNCalcModel model, NNCalcView view) {

        NaturalNumber top = model.top();
        NaturalNumber bottom = model.bottom();

        boolean a = false;
        boolean b = false;
        boolean c = false;
        boolean d = false;

        if (!bottom.isZero()) {
            c = true;
        }

        if (bottom.compareTo(top) <= 0) {
            d = true;
        }

        if (bottom.compareTo(INT_LIMIT) <= 0) {
            b = true;
        }

        if (bottom.compareTo(TWO) >= 0 && bottom.compareTo(INT_LIMIT) <= 0) {
            a = true;
        }
        view.updateTopDisplay(top);
        view.updateBottomDisplay(bottom);
        view.updateSubtractAllowed(d);
        view.updateDivideAllowed(c);
        view.updateRootAllowed(a);
        view.updatePowerAllowed(b);
    }

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public NNCalcController1(NNCalcModel model, NNCalcView view) {
        this.model = model;
        this.view = view;
        updateViewToMatchModel(model, view);
    }

    @Override
    public void processClearEvent() {
        /*
         * Get alias to bottom from model
         */
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update model in response to this event
         */
        bottom.clear();
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processSwapEvent() {
        /*
         * Get aliases to top and bottom from model
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update model in response to this event
         */
        NaturalNumber temp = top.newInstance();
        temp.transferFrom(top);
        top.transferFrom(bottom);
        bottom.transferFrom(temp);
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    // copy from bottom to top
    @Override
    public void processEnterEvent() {

        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        top.copyFrom(bottom);
//update view to match changes in the model
        updateViewToMatchModel(this.model, this.view);

    }

    //add top and bottom natural numbers
    @Override
    public void processAddEvent() {

        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        top.add(bottom);
        bottom.transferFrom(top);

        //update view to match changes
        updateViewToMatchModel(this.model, this.view);

    }

    //subtract value of bottom from top
    @Override
    public void processSubtractEvent() {

        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        top.subtract(bottom);
        bottom.transferFrom(top);

        //update view to match changes

        updateViewToMatchModel(this.model, this.view);

    }

    //multiple the values of top from bottom
    @Override
    public void processMultiplyEvent() {
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        top.multiply(bottom);
        bottom.transferFrom(top);

        //update view to match changes

        updateViewToMatchModel(this.model, this.view);

    }

    @Override

    //divide top by bottom
    public void processDivideEvent() {

        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        NaturalNumber remin = new NaturalNumber2(top.divide(bottom));

        bottom.transferFrom(top);
        top.transferFrom(remin);

        //update view to match changes

        updateViewToMatchModel(this.model, this.view);

    }

    //raise top to the value of bottom

    @Override
    public void processPowerEvent() {

        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        int expo = bottom.toInt();

        top.power(expo);

        bottom.transferFrom(top);

        //update view to match changes

        updateViewToMatchModel(this.model, this.view);

    }

    // Take the root of the top  with the degree by the bottom

    @Override
    public void processRootEvent() {

        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        int rootD = bottom.toInt();

        top.root(rootD);
        bottom.transferFrom(top);

        //update view to match changes

        updateViewToMatchModel(this.model, this.view);

    }

    // Add a new digit to the bottom by multiplying it by 10 and adding the value (digit)

    @Override
    public void processAddNewDigitEvent(int digit) {

        NaturalNumber bottom = this.model.bottom();

        bottom.multiplyBy10(digit);

        //update view to match changes

        updateViewToMatchModel(this.model, this.view);

    }

}
