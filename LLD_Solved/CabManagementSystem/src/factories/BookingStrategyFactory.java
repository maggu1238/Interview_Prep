package factories;

import enums.StrategyType;
import strategies.BookingStrategy;
import strategies.IdleTimeBookingStrategy;

public class BookingStrategyFactory implements IBookingStrategyFactory{

    @Override
    public BookingStrategy createBookingStrategy(StrategyType strategyType) {
        switch(strategyType){
            case Idle:
                return new IdleTimeBookingStrategy();
        }
        return null;
    }
}
