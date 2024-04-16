#include <vector>
#include <iostream>
// You may add other header files if needed
using namespace std;

template <typename T>
class  Matrix
{
     private:
        //Instances for row size and column size for Matrix
        int row,col;
        //Vector representation of matrix
        std::vector<std::vector<T>> data;
    // Please finish the Matrix template
    public:
       
        //Matrix Constructors
        Matrix<T>(std::vector<std::vector<T>> vec){
            this->data = vec;
            this->row = vec.size();
            this->col = vec[0].size();
        }

        //Member functions
        T sum(){
            T temp = 0;
            for(int i = 0; i < this->row; i++){
                for(int j = 0; j < this->col; j++){
                   temp += this->data[i][j];
                }
            }
            return temp;
        }

        Matrix<T> mul(Matrix<T> nMat){
            //Setup return matrix
            std::vector<std::vector<T>> nVec(this->row);
            for(int i =0; i<this->row; i++){
             nVec[i] = vector<T>(nMat.col);   
            }
            Matrix<T> result(nVec);

            T tSum = 0;
            T tMul = 0;
            //populate result matrix
            for(int j = 0; j<this->row; j++){
                for(int k = 0; k < nMat.col; k++){
                    result.data[j][k] = 0;
                    for(int l = 0; l < nMat.row; l++){
                        result.data[j][k] += this->data[j][l] * nMat.data[l][k];
                    }
                }
            }

            return result;
        }

        //Overloading output for Matrix
        friend std::ostream& operator<<(std::ostream& os, const Matrix<T>& mat){
            
            for(int i = 0; i < mat.row; i++){
                for(int j = 0; j < mat.col; j++){
                    os<<mat.data[i][j]<<" ";
                }
                os<< endl;
            }

            return os;
        }


   

};
