#include <iostream>
#include <vector>
#include "MyAlloc.h"

struct complex {
  double real;
  double img;
};


int main() {
    std::vector<int, MyAlloc<int>> v;
    for (int i = 0; i < 1000; i++) {
        v.push_back(i);
    }

    std::vector<complex, MyAlloc<complex>> nums;
    for (int i = 0; i < 1000; i++) {
        complex num1;
        num1.real = 10.0;
        num1.img = 20.0;
        nums.push_back(num1);
    }

    return 0;
}
