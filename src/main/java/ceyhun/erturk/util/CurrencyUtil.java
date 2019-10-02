package ceyhun.erturk.util;

import ceyhun.erturk.exception.CurrencyConvertException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CurrencyUtil {

    private static final ThreadLocal CURRANCY_DIFF = ThreadLocal.withInitial(() -> new HashMap<CurrencyDiff, Double>() {{
        put(new CurrencyDiff(CurrencyType.DOLLAR, CurrencyType.EURO), 0.92);
        put(new CurrencyDiff(CurrencyType.EURO, CurrencyType.DOLLAR), 1.09);
        put(new CurrencyDiff(CurrencyType.POUND, CurrencyType.DOLLAR), 1.23);
        put(new CurrencyDiff(CurrencyType.DOLLAR, CurrencyType.POUND), 0.81);
        put(new CurrencyDiff(CurrencyType.EURO, CurrencyType.POUND), 0.89);
        put(new CurrencyDiff(CurrencyType.POUND, CurrencyType.EURO), 1.13);
    }});

    public static Map<CurrencyDiff, Double> getCurrentyRates() {
        return (Map<CurrencyDiff, Double>) CURRANCY_DIFF.get();
    }


    public static double GetCurrencyDiff(CurrencyType receiverCurrencyType, CurrencyType senderCurrencyType) throws CurrencyConvertException {
        if (senderCurrencyType == receiverCurrencyType) {
            return 1.0;
        } else {
            Double diff = getCurrentyRates().get(new CurrencyDiff(senderCurrencyType, receiverCurrencyType));
            if (diff == null) {
                throw new CurrencyConvertException();
            } else {
                return diff;
            }

        }
    }

    public enum CurrencyType {
        DOLLAR(0),
        EURO(1),
        POUND(2);
        private final int currencyType;

        CurrencyType(final int currencyType) {
            this.currencyType = currencyType;
        }
    }

    private static class CurrencyDiff {
        CurrencyType currencyFrom;
        CurrencyType currencyTo;

        public CurrencyDiff(CurrencyType currencyFrom, CurrencyType currencyTo) {
            this.currencyFrom = currencyFrom;
            this.currencyTo = currencyTo;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CurrencyDiff)) return false;
            CurrencyDiff that = (CurrencyDiff) o;
            return currencyFrom == that.currencyFrom && currencyTo == that.currencyTo;
        }

        @Override
        public int hashCode() {
            return Objects.hash(currencyFrom, currencyTo);
        }
    }


}
