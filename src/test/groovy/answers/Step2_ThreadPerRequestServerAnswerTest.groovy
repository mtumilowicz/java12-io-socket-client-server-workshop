package answers
/**
 * Created by mtumilowicz on 2019-07-08.
 */
class Step2_ThreadPerRequestServerAnswerTest extends ServerTest {

    @Override
    def getServer() {
        new Step2_ThreadPerRequestServerAnswer()
    }
}
