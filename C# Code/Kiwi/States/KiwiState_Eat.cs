using UnityEngine;

namespace CSE5912.PenguinProductions
{
    public class KiwiState_Eat : IState
    {
        private readonly KiwiReferences kiwiReferences;
        private readonly GameObject itemToEat;

        public KiwiState_Eat(KiwiReferences kiwiReferences, GameObject itemToEat)
        {
            this.kiwiReferences = kiwiReferences;
            this.itemToEat = itemToEat;
        }
        public Color GizmoColor()
        {
            return Color.red;
        }

        public void OnEnter()
        {
            kiwiReferences.Animator.SetBool("attack", true);
            
            if(kiwiReferences.itemPool.Count < 3)
            {
                kiwiReferences.itemPool.Add(itemToEat);
                itemToEat.SetActive(false);
            }
        }

        public void OnExit()
        {
            kiwiReferences.Animator.SetBool("attack", false);
        }

        public void Tick()
        {
        }
    }
}
