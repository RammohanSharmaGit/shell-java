import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@Disabled
class ShellTest {
    private Evaluator evaluator = new Evaluator();

    @Test
    void evaluate_whenUnknownCommandShouldPrintUnknown() {
        String evaluation = evaluator.evaluate("echo random");
        Assertions.assertThat(evaluation).isEqualTo("random\n");
    }

    @Nested
    @DisplayName("Tests for 'echo' command")
    class EchoTests {
        @Test
        @DisplayName("echo with a single word argument")
        void echo_withSimpleArgument_returnsArgumentWithNewline() {
            String result = evaluator.evaluate("echo hello");
            Assertions.assertThat(result).isEqualTo("hello\n");
        }

        @Test
        @DisplayName("echo with multiple words should preserve spaces")
        void echo_withMultipleWords_returnsFullStringWithNewline() {
            String result = evaluator.evaluate("echo hello world again");
            Assertions.assertThat(result).isEqualTo("hello world again\n");
        }

        @Test
        @DisplayName("echo with no arguments returns null (Current Logic)")
        void echo_noArguments_returnsNull() {
            String result = evaluator.evaluate("echo");
            Assertions.assertThat(result).isNull();
        }
    }

    @Nested
    @DisplayName("Tests for 'type' command")
    class TypeTests {
        @ParameterizedTest
        @ValueSource(strings = {"echo", "type", "exit"})
        @DisplayName("type should identify built-in commands")
        void type_builtins_returnsBuiltinMessage(String command) {
            String result = evaluator.evaluate("type " + command);
            Assertions.assertThat(result).isEqualTo(command + " is a shell builtin\n");
        }

        @Test
        @DisplayName("type should return path for path commands")
        void type_listed_returnsPath() {
            String result = evaluator.evaluate("type bootdev");
            Assertions.assertThat(result).isEqualTo("bootdev is /Users/rammohansharma/go/bin/bootdev\n");
        }

        @Test
        @DisplayName("type should return not found for external/random commands")
        void type_unknown_returnsNotFound() {
            String result = evaluator.evaluate("type randomcmd");
            Assertions.assertThat(result).isEqualTo("randomcmd: not found\n");
        }

        @Test
        @DisplayName("type with no arguments returns null (Current Logic)")
        void type_noArguments_returnsNull() {
            String result = evaluator.evaluate("type");
            Assertions.assertThat(result).isNull();
        }
    }

    @Nested
    @DisplayName("Tests for unknown commands")
    class UnknownCommandTests {
        @Test
        @DisplayName("random strings should return command not found")
        void unknownCommand_returnsNotFoundMessage() {
            String result = evaluator.evaluate("invalidCommand");
            Assertions.assertThat(result).isEqualTo("invalidCommand: command not found\n");
        }

        @Test
        @DisplayName("unknown command with arguments should return full string in error")
        void unknownCommandWithArgs_returnsNotFoundMessage() {
            // Because you format using the full 'command' string in default case
            String result = evaluator.evaluate("random -la");
            Assertions.assertThat(result).isEqualTo("random -la: command not found\n");
        }
    }
}
