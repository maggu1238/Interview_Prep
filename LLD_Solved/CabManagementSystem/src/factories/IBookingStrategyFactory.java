package factories;

import enums.StrategyType;
import strategies.BookingStrategy;
import strategies.IdleTimeBookingStrategy;

public interface IBookingStrategyFactory {
    BookingStrategy createBookingStrategy(StrategyType strategyType);
}
