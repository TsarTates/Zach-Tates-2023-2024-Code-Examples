#include <iostream>
#include <vector>

using namespace std;

template <typename T>
class MyAlloc{
    public:
        //Necessary for any custom allocator
        typedef T value_type;

        //Used noexcept for constructor to speed up performance slightly
        MyAlloc() noexcept{}

        //Allocates data based on the standard byte size for each type
        T * allocate(std::size_t size){
            return (T*) malloc(sizeof(T)*size);
        }

        //Frees memory allocated to the pointer
        void deallocate(T * ptr, std::size_t size){
            delete(ptr);
        }
       

};
