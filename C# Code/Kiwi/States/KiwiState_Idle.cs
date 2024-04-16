using UnityEngine;

namespace CSE5912.PenguinProductions
{
    public class KiwiState_Idle : IState
    {
        private readonly KiwiReferences kiwiReferences;

        private readonly float waitForSeconds;
        private float deadline;

        public KiwiState_Idle(KiwiReferences kiwiReferences, float waitForSeconds)
        {
            this.kiwiReferences = kiwiReferences;
            this.waitForSeconds = waitForSeconds;
        }
        public Color GizmoColor()
        {
            return Color.red;
        }

        public void OnEnter()
        {
            kiwiReferences.Animator.SetBool("idle", true);
            deadline = Time.time + waitForSeconds;
        }

        public void OnExit()
        {
            kiwiReferences.Animator.SetBool("idle", false);
        }

        public void Tick() { }

        public bool IsDone()
        {
            return Time.time >= deadline;
        }
    }
}
