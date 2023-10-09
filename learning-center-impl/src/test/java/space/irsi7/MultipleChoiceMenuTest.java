package space.irsi7;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import space.irsi7.controllers.MultipleChoiceMenu;

import static org.mockito.Mockito.verify;

public class MultipleChoiceMenuTest {
    @Mock
    MultipleChoiceMenu multipleChoiceMenu = Mockito.mock(MultipleChoiceMenu.class);

    @Test
    public void chose_test(){
        multipleChoiceMenu.chooseOne();
        verify(multipleChoiceMenu).chooseOne();
    }
}
