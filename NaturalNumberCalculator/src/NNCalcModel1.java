import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Model class.
 *
 * @author Yonis Mohamud
 */
public final class NNCalcModel1 implements NNCalcModel {

    /**
     * Model variables.
     */
    private final NaturalNumber top, bottom;

    /**
     * No argument constructor.
     */
    public NNCalcModel1() {
        this.top = new NaturalNumber2();
        this.bottom = new NaturalNumber2();
    }

    //return top NN
    @Override
    public NaturalNumber top() {
        return this.top;
    }

    // return bottom NN
    @Override
    public NaturalNumber bottom() {

        return this.bottom;
    }

}
