
public class prime
{
    public boolean isPrime(int n)
    {
        int i = 2;
        while (i < n) {
            if (n % i == 0) {
                return false;
            }
            i = i + 1;
        }
        return true;
    }
}
