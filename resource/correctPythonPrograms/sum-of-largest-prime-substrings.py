class Solution:

  def is_prime(self, n: int) -> bool:
    if n == 1:
      return False
    if n <= 3:
      return True

    if n % 2 == 0 or n % 3 == 0:
      return False

    for i in range(5, int(n ** 0.5) + 1, 6):
      if n % i == 0 or n % (i + 2) == 0:
        return False
    return True

  def sumOfLargestPrimes(self, s: str) -> int:
    primes = []
    for i in range(len(s)):
      for j in range(i, len(s)):
        v = int(s[i:j + 1])
        if self.is_prime(v):
          primes.append(v)

    primes.sort(reverse=True)

    res, prev, cnt = 0, -1, 0
    for v in primes:
      if v != prev:
        prev = v
        res += v
        cnt += 1
        if cnt == 3:
          break

    return res
