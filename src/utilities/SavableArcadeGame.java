package utilities;
import java.util.Optional;
public interface SavableArcadeGame {
public String getName();
public java.awt.Component getMainFrame();
public default Optional<SavableArcadeGame> returnCurrentGame()
{
	return Optional.empty();
			
}
}
