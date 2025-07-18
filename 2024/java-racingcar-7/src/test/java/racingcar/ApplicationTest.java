package racingcar;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomNumberInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

class ApplicationTest extends NsTest {
    private static final int MOVING_FORWARD = 4;
    private static final int STOP = 3;

    @Test
    void 기능_테스트() {
        assertRandomNumberInRangeTest(
            () -> {
                run("pobi,woni", "1");
                assertThat(output()).contains("pobi : -", "woni : ", "최종 우승자 : pobi");
            },
            MOVING_FORWARD, STOP
        );
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException("pobi,javaji", "1"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 기능_테스트2() {
        assertRandomNumberInRangeTest(
                () -> {
                    run("pobi,woni", "4");
                    assertThat(output()).contains("pobi : ---", "woni : --", "최종 우승자 : pobi");
                },
                MOVING_FORWARD, STOP,
                MOVING_FORWARD, STOP,
                MOVING_FORWARD, MOVING_FORWARD,
                STOP, MOVING_FORWARD
        );
    }

    @Test
    void 공동_우승1() {
        assertRandomNumberInRangeTest(
                () -> {
                    run("pobi,woni", "3");
                    assertThat(output()).contains("pobi : ---", "woni : ---", "최종 우승자 : pobi, woni");
                },
                MOVING_FORWARD, MOVING_FORWARD,
                MOVING_FORWARD, MOVING_FORWARD,
                MOVING_FORWARD, MOVING_FORWARD
        );
    }

    @Test
    void 공동_우승2() {
        assertRandomNumberInRangeTest(
                () -> {
                    run("pobi,woni,loni", "1");
                    assertThat(output()).contains("pobi : ", "woni : ", "loni : ", "최종 우승자 : pobi, woni, loni");
                },
                STOP, STOP, STOP
        );
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
