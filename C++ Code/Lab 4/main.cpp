#include <iostream>
#include <vector>
#include "matrix.h"

int main()
{
    std::vector<std::vector<float>> a =
    {
        {1.0, 2.5, 3.0},
        {4.0, 5.5, 6.0}
    };
    Matrix<float> mata(a);
    std::vector<std::vector<float>> b =
    {
        {1.0, 2.0},
        {3.0, 4.5},
        {5.0, 6.0}
    };
    Matrix<float> matb(b);
    // Matrix product of mata and matb
    auto matc = mata.mul(matb);

    std::cout << matc << std::endl;
    std::cout << mata << std::endl;
    std::cout << mata.sum() << std::endl;

    std::vector<std::vector<int>> x = {{1, 2}, {3, 4}};
    std::vector<std::vector<int>> y = {{2},{3}};
    Matrix<int> matx(x);
    Matrix<int> maty(y);
    // Matrix product of matx and maty
    auto matz = matx.mul(maty);

    std::cout << matz << std::endl;

    std::vector<std::vector<double>> u = {{1.0, 2.0}, {3.0, 4.0}};
    std::vector<std::vector<double>> v = {{2.0, 3.0}, {3.5, 5.5}};
    Matrix<double> matu(u);
    Matrix<double> matv(v);
    // Matrix product of matu and matv
    auto matw = matu.mul(matv);

    std::cout << matw << std::endl;
    return 0;
}
