using UnityEngine;

namespace CSE5912.PenguinProductions
{
    public class KiwiState_Attack : IState
    {
        private readonly KiwiReferences kiwiReferences;

        public KiwiState_Attack(KiwiReferences kiwiReferences)
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
            AudioManager.Singleton.PersistentPlayOneShot(kiwiReferences.kiwiSFX[1], AudioManager.AudioGroup.FX);
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
