using UnityEngine;

namespace CSE5912.PenguinProductions
{
    public class KiwiState_SpitUp :IState
    {
        private readonly KiwiReferences kiwiReferences;

        public KiwiState_SpitUp(KiwiReferences kiwiReferences)
        {
            this.kiwiReferences = kiwiReferences;
        }
        public Color GizmoColor()
        {
            return Color.red;
        }

        public void OnEnter()
        {
            kiwiReferences.Animator.SetBool("attack", true);
           foreach (var item in kiwiReferences.itemPool)
            {
                item.transform.position = kiwiReferences.transform.position;
                item.SetActive(true);
            }
            kiwiReferences.itemPool.Clear();
        }

        public void OnExit()
        {
            kiwiReferences.Animator.SetBool("attack", false);
        }

        public void Tick() { }
    }
}
