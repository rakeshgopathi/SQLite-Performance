package co.jasonwyatt.sqliteperf.inserts;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import co.jasonwyatt.sqliteperf.App;
import co.jasonwyatt.sqliteperf.R;
import co.jasonwyatt.sqliteperf.TestCase;
import co.jasonwyatt.sqliteperf.TestSuiteFragment;
import co.jasonwyatt.sqliteperf.inserts.integers.IntegerInsertsRawTransactionCase;
import co.jasonwyatt.sqliteperf.inserts.integers.IntegerInsertsTestCase;
import co.jasonwyatt.sqliteperf.inserts.integers.IntegerInsertsTransactionCase;
import co.jasonwyatt.sqliteperf.inserts.tracks.InsertsTestCase;
import co.jasonwyatt.sqliteperf.inserts.tracks.InsertsTransactionTestCase;
import co.jasonwyatt.sqliteperf.inserts.tracks.RawTestCase;

/**
 * @author jason
 */

public class InsertsVsExecSQLFragment extends TestSuiteFragment {
    @Override
    protected String getChartTitle() {
        return App.getInstance().getString(R.string.insert_performance_insert_vs_execsql);
    }

    @Override
    protected Map<TestScenarioMetadata, TestCase[]> getTestScenarios() {
        Map<TestScenarioMetadata, TestCase[]> result = new HashMap<>(4);

        result.put(new TestScenarioMetadata("simple db.insert()", 0xFFb71c1c, 10), new TestCase[]{
                new IntegerInsertsTransactionCase(1000, 2),
                new IntegerInsertsTransactionCase(10000, 3),
                new IntegerInsertsTransactionCase(100000, 4)
        });

        result.put(new TestScenarioMetadata("simple db.execSQL()", 0xFFf05545, 10), new TestCase[]{
                new IntegerInsertsRawTransactionCase(1000, 2),
                new IntegerInsertsRawTransactionCase(10000, 3),
                new IntegerInsertsRawTransactionCase(100000, 4)
        });

        result.put(new TestScenarioMetadata("tracks db.insert()", 0xFF4a148c, 10), new TestCase[] {
                new InsertsTransactionTestCase(1000, 2),
                new InsertsTransactionTestCase(10000, 3),
                new InsertsTransactionTestCase(100000, 4)
        });

        result.put(new TestScenarioMetadata("tracks db.execSQL()", 0xFF7c43bd, 10), new TestCase[] {
                new RawTestCase(1000, 2),
                new RawTestCase(10000, 3),
                new RawTestCase(100000, 4)
        });

        return result;
    }

    @Override
    protected MetricsVariableTransformer getMetricsTransformer() {
        return new MetricsVariableTransformer() {
            @Override
            public String variableValueForDisplay(float variableValue) {
                return String.format(Locale.ENGLISH, "%d inserts", (int) Math.pow(10, variableValue+1));
            }

            @Override
            public String elapsedTimeValueForDisplay(float elapsedTimeMs) {
                return String.format(Locale.ENGLISH, "%.3fs", elapsedTimeMs / 1000f);
            }
        };
    }
}
