package lotto;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Arrays;
import java.util.List;
import model.Lotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LottoTest {
    @Test
    void 로또_번호의_개수가_6개가_넘어가면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6, 7)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    @Test
    void 로또_번호에_중복된_숫자가_있으면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 당첨_번호_일치_테스트() {
        List<Integer> lottoNumber = Arrays.asList(1, 2, 3, 4, 5, 6);
        Lotto lotto = new Lotto(lottoNumber);
        List<Integer> winningNumber = Arrays.asList(1, 2, 3, 4, 5, 6);

        int matchingcount = lotto.countMatchingNumbers(winningNumber);
        assertThat(matchingcount).isEqualTo(6);
    }

    @Test
    void 보너스_번호_확인() {
        List<Integer> lottoNumber = Arrays.asList(1, 2, 3, 4, 5, 6);
        Lotto lotto = new Lotto(lottoNumber);

        boolean containsBonus = lotto.containsBonusNumber(1);
        assertThat(containsBonus).isTrue();

        containsBonus = lotto.containsBonusNumber(7);
        assertThat(containsBonus).isFalse();
    }
}
